import os

def generate_large_afp(output_path, size_mb=1):
    # DOC00001 in EBCDIC IBM273 (German)
    # Based on UtilCharacterEncoding.java:
    # 'D' -> 0xC4
    # 'O' -> 0xD6
    # 'C' -> 0xC3
    # '0' -> 0xF0
    # '1' -> 0xF1
    # IBM273: 'D' is 0xC4, 'O' is 0xD6, 'C' is 0xC3, '0' is 0xF0, '1' is 0xF1.
    # It seems same as IBM500 for these chars.
    name_ebcdic = bytes([0xC4, 0xD6, 0xC3, 0xF0, 0xF0, 0xF0, 0xF0, 0xF1])

    # BDT: 5A + length(2) + type(3) + flag(1) + reserved(2) + payload
    bdt = bytes([0x5A, 0x00, 0x10, 0xD3, 0xA8, 0xA8, 0x00, 0x00, 0x00]) + name_ebcdic

    # NOP: 5A + length(2) + D3EEEE + flag(1) + reserved(2) + payload
    # Let's use NOPs to fill space.
    # Max length of SF is 32767. Payload max is 32767 - 8 = 32759.
    # Let's use payload of 32000. Total length = 32008 (0x7D08)
    nop_payload = b"This is a NOP field to fill space and test processing speed. " * 500
    nop_payload = nop_payload[:32000]
    nop_header = bytes([0x5A, 0x7D, 0x08, 0xD3, 0xEE, 0xEE, 0x00, 0x00, 0x00])
    nop_field = nop_header + nop_payload

    # EDT: 5A + length(2) + type(3) + flag(1) + reserved(2) + payload
    edt = bytes([0x5A, 0x00, 0x10, 0xD3, 0xA9, 0xA8, 0x00, 0x00, 0x00]) + name_ebcdic

    os.makedirs(os.path.dirname(output_path), exist_ok=True)
    with open(output_path, 'wb') as f:
        f.write(bdt)
        num_nops = (size_mb * 1024 * 1024) // len(nop_field)
        for _ in range(num_nops):
            f.write(nop_field)
        f.write(edt)

if __name__ == "__main__":
    generate_large_afp("src/test/resources/afp/large_ibm273.afp", size_mb=1)
