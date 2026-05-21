import os

def generate_big_ibm273_afp(output_path, size_mb=10):
    # DOC00001 in EBCDIC IBM273 (same as IBM500 for these chars)
    name_ebcdic = bytes([0xC4, 0xD6, 0xC3, 0xF0, 0xF0, 0xF0, 0xF0, 0xF1])

    # BDT: 5A + length(2) + type(3) + flag(1) + reserved(2) + payload
    bdt = bytes([0x5A, 0x00, 0x10, 0xD3, 0xA8, 0xA8, 0x00, 0x00, 0x00]) + name_ebcdic

    chunk_text = "Dies ist ein Test mit deutschen Umlauten: äöüÄÖÜß. " * 20
    try:
        chunk_bytes = chunk_text.encode('cp273')
    except LookupError:
        chunk_bytes = chunk_text.encode('latin1')

    # Ensure chunk_bytes is not too long for the 1-byte length in TRN
    if len(chunk_bytes) > 250:
        chunk_bytes = chunk_bytes[:250]

    os.makedirs(os.path.dirname(output_path), exist_ok=True)
    with open(output_path, 'wb') as f:
        f.write(bdt)

        bytes_written = len(bdt)
        target_bytes = size_mb * 1024 * 1024

        while bytes_written < target_bytes:
            # BPG
            bpg = bytes([0x5A, 0x00, 0x10, 0xD3, 0xA8, 0xAF, 0x00, 0x00, 0x00]) + name_ebcdic
            f.write(bpg)
            bytes_written += len(bpg)

            # PTX
            payload = bytes([0x2B, 0xD3, len(chunk_bytes) + 2]) + chunk_bytes
            sf_len = len(payload) + 8
            ptx_header = bytes([0x5A, (sf_len >> 8) & 0xFF, sf_len & 0xFF, 0xD3, 0xEE, 0x9B, 0x00, 0x00, 0x00])
            f.write(ptx_header + payload)
            bytes_written += len(ptx_header) + len(payload)

            # EPG
            epg = bytes([0x5A, 0x00, 0x10, 0xD3, 0xA9, 0xAF, 0x00, 0x00, 0x00]) + name_ebcdic
            f.write(epg)
            bytes_written += len(epg)

        # EDT
        edt = bytes([0x5A, 0x00, 0x10, 0xD3, 0xA9, 0xA8, 0x00, 0x00, 0x00]) + name_ebcdic
        f.write(edt)

if __name__ == "__main__":
    generate_big_ibm273_afp("src/test/resources/afp/big_german.afp", size_mb=20)
