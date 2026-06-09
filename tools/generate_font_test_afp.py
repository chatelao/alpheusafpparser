import os

def create_sf(type_id, data=b""):
    length = len(data) + 8
    header = bytes([0x5A, (length >> 8) & 0xFF, length & 0xFF]) + bytes(type_id) + bytes([0x00, 0x00, 0x00])
    return header + data

def create_pgd(x_units=14400, y_units=14400, x_size=12240, y_size=15840):
    # Unit base: 0x00 = 10 inches
    data = bytes([0x00, 0x00])
    data += x_units.to_bytes(2, 'big')
    data += y_units.to_bytes(2, 'big')
    data += x_size.to_bytes(3, 'big')
    data += y_size.to_bytes(3, 'big')
    data += bytes([0x00, 0x00, 0x00])
    return create_sf([0xD3, 0xA6, 0xAF], data)

def ebcdic(s, length=8):
    return s.encode('cp500').ljust(length, b'\x40')

def create_mcf2_rg(lid, name):
    name_ebcdic = ebcdic(name)
    # Triplet 0x02: Fully Qualified Name (Coded Font Name)
    fqn = bytes([len(name_ebcdic) + 2, 0x02, 0x8E]) + name_ebcdic
    # Triplet 0x24: Resource Local Identifier (Coded Font)
    rli = bytes([0x04, 0x24, 0x05, lid])
    rg_content = fqn + rli
    rg_len = len(rg_content) + 2
    return rg_len.to_bytes(2, 'big') + rg_content

def create_mcf2(rgs):
    data = b""
    for rg in rgs:
        data += rg
    return create_sf([0xD3, 0xAB, 0x8A], data)

def create_ptx(controls):
    data = b""
    for c in controls:
        data += c
    return create_sf([0xD3, 0xEE, 0x9B], data)

def scfl(lid):
    return bytes([0x2B, 0xD3, 0x03, 0xF0, lid])

def aml(i, b):
    ami = bytes([0x2B, 0xD3, 0x04, 0xC6, (i >> 8) & 0xFF, i & 0xFF])
    amb = bytes([0x2B, 0xD3, 0x04, 0xD2, (b >> 8) & 0xFF, b & 0xFF])
    return ami + amb

def trn(text):
    text_ebcdic = text.encode('cp500')
    l = len(text_ebcdic) + 2
    return bytes([0x2B, 0xD3, l, 0xDA]) + text_ebcdic

def generate():
    # Use standard names that PdfFontRegistry knows
    rgs = [
        create_mcf2_rg(1, "X0H20008"),
        create_mcf2_rg(2, "X0H20012"),
        create_mcf2_rg(3, "X0H20024"),
        create_mcf2_rg(4, "X0H30012")
    ]

    mcf = create_mcf2(rgs)
    pgd = create_pgd()

    doc_name = ebcdic("DOC1")
    page_name = ebcdic("PAGE1")
    aeg_name = ebcdic("AEG1")

    bdt = create_sf([0xD3, 0xA8, 0xA8], doc_name)
    edt = create_sf([0xD3, 0xA9, 0xA8], doc_name)
    bpg = create_sf([0xD3, 0xA8, 0xAF], page_name)
    epg = create_sf([0xD3, 0xA9, 0xAF], page_name)
    bag = create_sf([0xD3, 0xA8, 0xC9], aeg_name)
    eag = create_sf([0xD3, 0xA9, 0xC9], aeg_name)

    # Coordinates in 1440 DPI
    ptx = create_ptx([
        scfl(1), aml(1440, 1440), trn("Helvetica 8pt"),
        scfl(2), aml(1440, 2880), trn("Helvetica 12pt"),
        scfl(3), aml(1440, 4320), trn("Helvetica 24pt"),
        scfl(4), aml(1440, 5760), trn("Helvetica Bold 12pt")
    ])

    os.makedirs("src/test/resources/afp", exist_ok=True)
    with open("src/test/resources/afp/font_test.afp", "wb") as f:
        f.write(bdt)
        f.write(bpg)
        f.write(bag)
        f.write(pgd)
        f.write(mcf)
        f.write(eag)
        f.write(ptx)
        f.write(epg)
        f.write(edt)

if __name__ == "__main__":
    generate()
