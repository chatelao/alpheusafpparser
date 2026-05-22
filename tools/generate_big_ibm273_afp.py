import os

def generate_ibm273_afp(output_path, size_mb=1):
    # DOC00001 in EBCDIC IBM273
    name_ebcdic = bytes([0xC4, 0xD6, 0xC3, 0xF0, 0xF0, 0xF0, 0xF0, 0xF1])

    # BDT: 5A + length(2) + type(3) + flag(1) + reserved(2) + payload
    bdt = bytes([0x5A, 0x00, 0x10, 0xD3, 0xA8, 0xA8, 0x00, 0x00, 0x00]) + name_ebcdic

    # Fill with many small NOPs to increase overhead
    target_size = size_mb * 1024 * 1024
    current_size = len(bdt) + 16 # for EDT

    nops = []
    while current_size < target_size:
        remaining = target_size - current_size
        # Small NOPs
        payload_len = 32
        if remaining < payload_len + 9: break

        sf_len = payload_len + 8
        nop = bytes([0x5A, (sf_len >> 8) & 0xFF, sf_len & 0xFF, 0xD3, 0xEE, 0xCC, 0x00, 0x00, 0x00]) + b'A' * payload_len
        nops.append(nop)
        current_size += len(nop)

    # EDT
    edt = bytes([0x5A, 0x00, 0x10, 0xD3, 0xA9, 0xA8, 0x00, 0x00, 0x00]) + name_ebcdic

    os.makedirs(os.path.dirname(output_path), exist_ok=True)
    with open(output_path, 'wb') as f:
        f.write(bdt)
        for nop in nops:
            f.write(nop)
        f.write(edt)

if __name__ == "__main__":
    generate_ibm273_afp("src/test/resources/afp/big_ibm273_overhead.afp", 1)
