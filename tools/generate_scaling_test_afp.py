import os

def create_sf(type_id, data=b""):
    length = len(data) + 8
    header = bytes([0x5A, (length >> 8) & 0xFF, length & 0xFF]) + bytes(type_id) + bytes([0x00, 0x00, 0x00])
    return header + data

def create_pgd(x_units=1440, y_units=1440, x_size=11520, y_size=16200):
    # x_units_base=0 (Inches/10), y_units_base=0
    # units per base (2 bytes)
    # size (3 bytes)
    # reserved (3 bytes)
    data = bytes([0x00, 0x00])
    data += x_units.to_bytes(2, 'big')
    data += y_units.to_bytes(2, 'big')
    data += x_size.to_bytes(3, 'big')
    data += y_size.to_bytes(3, 'big')
    data += bytes([0x00, 0x00, 0x00])
    return create_sf([0xD3, 0xA6, 0xAF], data)

def create_mcf2_font(lid, name_ebcdic):
    # MCF-2 Repeating Group
    # RG Length (2 bytes)
    # Triplet: FQN (0x02) - Coded Font Name Reference (0x07)
    # Triplet: RLI (0x24) - Coded Font (0x05)

    # FQN
    fqn = bytes([len(name_ebcdic) + 2, 0x02, 0x07]) + name_ebcdic
    # RLI
    rli = bytes([0x04, 0x24, 0x05, lid])

    rg_content = fqn + rli
    rg_len = len(rg_content) + 2
    data = rg_len.to_bytes(2, 'big') + rg_content
    return create_sf([0xD3, 0xAB, 0x8A], data)

def create_ptx(text_ebcdic):
    # TRN (0xDA)
    # length = len(text) + 2
    trn = bytes([len(text_ebcdic) + 2, 0xDA]) + text_ebcdic
    return create_sf([0xD3, 0xEE, 0x9B], trn)

def generate_afps():
    os.makedirs("src/test/resources/afp", exist_ok=True)

    # DOC00001 in EBCDIC
    doc_name = bytes([0xC4, 0xD6, 0xC3, 0xF0, 0xF0, 0xF0, 0xF0, 0xF1])
    # PAGE0001 in EBCDIC
    page_name = bytes([0xD7, 0xC1, 0xC7, 0xC5, 0xF0, 0xF0, 0xF0, 0xF1])
    # AEG00001 in EBCDIC
    aeg_name = bytes([0xC1, 0xC5, 0xC7, 0xF0, 0xF0, 0xF0, 0xF0, 0xF1])
    # C0H200 in EBCDIC (8 bytes)
    font_name = bytes([0xC3, 0xF0, 0xC8, 0xF2, 0xF0, 0xF0, 0x40, 0x40])
    # "Scaling Test" in EBCDIC
    text = bytes([0xE2, 0x83, 0x81, 0x93, 0x89, 0x95, 0x87, 0x40, 0xE3, 0x85, 0xa2, 0xa3])

    bdt = create_sf([0xD3, 0xA8, 0xA8], doc_name)
    edt = create_sf([0xD3, 0xA9, 0xA8], doc_name)
    bpg = create_sf([0xD3, 0xA8, 0xAF], page_name)
    epg = create_sf([0xD3, 0xA9, 0xAF], page_name)
    bag = create_sf([0xD3, 0xA8, 0xC9], aeg_name)
    eag = create_sf([0xD3, 0xA9, 0xC9], aeg_name)

    pgd = create_pgd()
    mcf = create_mcf2_font(1, font_name)
    ptx = create_ptx(text)

    # Baseline: No document-level PGD, only page-level PGD
    with open("src/test/resources/afp/baseline_scaling.afp", "wb") as f:
        f.write(bdt)
        f.write(bpg)
        f.write(bag)
        f.write(pgd)
        f.write(mcf)
        f.write(eag)
        f.write(ptx)
        f.write(epg)
        f.write(edt)

    # Redundant: Document-level PGD AND page-level PGD
    with open("src/test/resources/afp/redundant_scaling.afp", "wb") as f:
        f.write(bdt)
        f.write(pgd) # Document level
        f.write(bpg)
        f.write(bag)
        f.write(pgd) # Page level
        f.write(mcf)
        f.write(eag)
        f.write(ptx)
        f.write(epg)
        f.write(edt)

if __name__ == "__main__":
    generate_afps()
