import os
import argparse

def make_ptx(payload):
    sfi_type = bytes.fromhex('d3ee9b')
    sfi_flags = b'\x00'
    sfi_res = b'\x00\x00'
    total_len = len(payload) + 9
    return b'\x5a' + total_len.to_bytes(2, 'big') + sfi_type + sfi_flags + sfi_res + payload

def make_trn(data):
    ll = len(data) + 2
    return b'\x2b\xd3' + ll.to_bytes(1, 'big') + b'\xda' + data

def generate_data(output_dir, num_files, ptx_per_file):
    os.makedirs(output_dir, exist_ok=True)

    trn_payload = b'A' * 250
    trn_seq = make_trn(trn_payload)
    # One PTX with ~32k
    ptx_payload = trn_seq * 127
    ptx_sf = make_ptx(ptx_payload)

    content = ptx_sf * ptx_per_file

    for i in range(num_files):
        with open(os.path.join(output_dir, f'test_{i}.afp'), 'wb') as f:
            f.write(content)
    print(f"Generated {num_files} files in {output_dir}")

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Generate AFP test data for profiling.')
    parser.add_argument('--dir', default='profile_dir', help='Output directory')
    parser.add_argument('--files', type=int, default=10, help='Number of files')
    parser.add_argument('--ptx', type=int, default=3, help='PTX fields per file')
    args = parser.parse_args()
    generate_data(args.dir, args.files, args.ptx)
