import os
import random

# Hex codes for Structured Fields (SF)
SF = {
    'BNG': b'\xD3\xA8\xAD',
    'ENG': b'\xD3\xA9\xAD',
    'GAD': b'\xD3\xEE\xBB',
    'GDD': b'\xD3\xA6\xBB',
    'IDD': b'\xD3\xA6\xFB',
    'IMM': b'\xD3\xAB\xCC',
    'IPD': b'\xD3\xEE\xFB',
    'IPS': b'\xD3\xAF\x5F',
    'MCF': b'\xD3\xB1\x8A',
    'MIO': b'\xD3\xAB\xFB',
    'MPS': b'\xD3\xB1\x5F',
    'NOP': b'\xD3\xEE\xEE',
    'OBD': b'\xD3\xA6\x6B',
    'OBP': b'\xD3\xAC\x6B',
    'PGD': b'\xD3\xA6\xAF',
    'PTD': b'\xD3\xA6\x9B',
    'PTX': b'\xD3\xEE\x9B',
    'TLE': b'\xD3\xA0\x90',
}

# Hex codes for PTOCA Control Sequences (CS)
CS = {
    'AMB': b'\xD2',
    'AMI': b'\xC6',
    'DBR': b'\xE6',
    'DIR': b'\xE4',
    'NOP': b'\xF8',
    'SCFL': b'\xF0',
    'SEC': b'\x80',
    'SIA': b'\xC2',
    'STO': b'\xF6',
    'SVI': b'\xC4',
    'TRN': b'\xDA',
}

# Hex codes for GOCA Drawing Orders
GOCA = {
    'GBAR': b'\x68',
    'GBSEG': b'\x70',
    'GEAR': b'\x60',
    'GSCP': b'\x21',
}

def make_sf(type_bytes, payload=b'', flag=0x00, reserved=0x0000):
    length = len(payload) + 8
    header = b'\x5A' + length.to_bytes(2, 'big') + type_bytes + bytes([flag]) + reserved.to_bytes(2, 'big')
    return header + payload

def make_cs(type_byte, payload=b'', chained=False):
    type_code = type_byte[0]
    if chained:
        type_code += 1
    length = len(payload) + 2
    return b'\x2B\xD3' + bytes([length, type_code]) + payload

def make_goca(type_byte, payload=b''):
    if type_byte == b'\x70': # GBSEG special case
        # GBSEG: 70 + 0C (len) + name(4) + flag(1) + prop(1) + dlen(2) + name(4) = 14 bytes
        # We'll set dlen to length of payload
        dlen = len(payload)
        return b'\x70\x0C' + b'SEG1' + b'\x00\x00' + dlen.to_bytes(2, 'big') + b'PRED' + payload
    elif type_byte in [b'\x68', b'\x60']: # GBAR, GEAR
        return type_byte + b'\x00' # Length 0 following data
    else:
        length = len(payload)
        return type_byte + bytes([length]) + payload

def generate_testset():
    output_dir = 'test/big'
    os.makedirs(output_dir, exist_ok=True)

    total_files = 261
    target_counts = {
        'BNG': 261, 'ENG': 261, 'IMM': 261,
        'GAD': 433, 'GBSEG': 433, 'GBAR': 3423, 'GEAR': 3423, 'GSCP': 20177,
        'IDD': 1152, 'IPD': 3485, 'MIO': 1152, 'TLE': 10494,
        'PTX': 5907, 'AMB': 83851, 'AMI': 83851, 'TRN': 75017,
        'DIR': 7919, 'DBR': 915, 'PTOCA_NOP': 5907, 'SCFL': 18951,
        'SEC': 10645, 'SIA': 1177, 'STO': 2951, 'SVI': 10373,
        'SF_NOP': 11128, 'MCF': 1002, 'PGD': 1002, 'PTD': 1002,
        'OBD': 1256, 'OBP': 1256, 'GDD': 104, 'IPS': 693, 'MPS': 693
    }

    file_sizes = [700 * 1024] * 1 + \
                 [8 * 1024] * 8 + \
                 [7 * 1024] * 120 + \
                 [6 * 1024] * 53
    remaining_files = total_files - len(file_sizes)
    current_total_sum = sum(file_sizes)
    remaining_size = (9216 * 1024) - current_total_sum
    avg_rem_size = remaining_size // remaining_files
    file_sizes += [avg_rem_size] * remaining_files
    total_target_size = sum(file_sizes)

    distributed_counts = {k: [0]*total_files for k in target_counts}
    for k, total in target_counts.items():
        if k in ['BNG', 'ENG', 'IMM']:
            for i in range(total_files): distributed_counts[k][i] = 1
            continue
        current_total = 0
        for i in range(total_files):
            count = int(total * (file_sizes[i] / total_target_size))
            distributed_counts[k][i] = count
            current_total += count
        remainder = total - current_total
        for _ in range(remainder):
            idx = random.randint(0, total_files - 1)
            distributed_counts[k][idx] += 1

    for i in range(total_files):
        file_path = os.path.join(output_dir, f'file_{i:03d}.afp')
        content = b''
        content += make_sf(SF['BNG'], b'GROUP' + f'{i:03d}'.encode())
        content += make_sf(SF['IMM'], b'MEDIUM')

        # Valid minimal payloads
        payloads = {
            'TLE': b'\x00\x00' + os.urandom(8),
            'MCF': b'\x00\x00\x00\x00\x00\x08' + os.urandom(10), # MCF Format 2 needs 6 bytes header + RGs
            'PGD': b'\x00' * 30,
            'PTD': b'\x00' * 20,
            'OBD': b'\x00' * 10,
            'OBP': b'\x00' * 30,
            'GDD': b'\x00' * 10,
            'IPS': b'\x00' * 20,
            'MPS': b'\x00' * 30,
            'MIO': b'\x00' * 10,
            'IDD': b'\x00' * 10,
            'NOP': b'\x00' * 2
        }

        for sf_type in ['TLE', 'SF_NOP', 'MCF', 'PGD', 'PTD', 'OBD', 'OBP', 'GDD', 'IPS', 'MPS', 'MIO', 'IDD']:
            key = sf_type if sf_type != 'SF_NOP' else 'NOP'
            p = payloads.get(key, b'')
            for _ in range(distributed_counts[sf_type][i]):
                content += make_sf(SF[key], p)

        # GAD fields
        all_goca = []
        for _ in range(distributed_counts['GSCP'][i]): all_goca.append(make_goca(GOCA['GSCP'], b'\x00\x00\x00\x00'))
        for _ in range(distributed_counts['GBAR'][i]): all_goca.append(make_goca(GOCA['GBAR']))
        for _ in range(distributed_counts['GEAR'][i]): all_goca.append(make_goca(GOCA['GEAR']))

        # Wrap GOCA in GBSEG if possible
        num_gbseg = distributed_counts['GBSEG'][i]
        wrapped_goca = []
        if num_gbseg > 0:
            orders_per_seg = len(all_goca) // num_gbseg
            for g in range(num_gbseg):
                start = g * orders_per_seg
                end = (g+1) * orders_per_seg if g < num_gbseg - 1 else len(all_goca)
                wrapped_goca.append(make_goca(GOCA['GBSEG'], b''.join(all_goca[start:end])))
        else:
            wrapped_goca = all_goca

        num_gad = distributed_counts['GAD'][i]
        if num_gad > 0:
            segs_per_gad = len(wrapped_goca) // num_gad
            for g in range(num_gad):
                payload = b''
                start = g * segs_per_gad
                end = (g+1) * segs_per_gad if g < num_gad - 1 else len(wrapped_goca)
                for s in range(start, end): payload += wrapped_goca[s]
                while len(payload) > 30000:
                    content += make_sf(SF['GAD'], payload[:30000])
                    payload = payload[30000:]
                content += make_sf(SF['GAD'], payload)
        elif len(wrapped_goca) > 0:
            content += make_sf(SF['GAD'], b''.join(wrapped_goca))

        # IPD
        for _ in range(distributed_counts['IPD'][i]): content += make_sf(SF['IPD'], b'\x00'*10)

        # PTX
        ptoca_seqs = []
        for _ in range(distributed_counts['AMB'][i]): ptoca_seqs.append(make_cs(CS['AMB'], b'\x00\x00'))
        for _ in range(distributed_counts['AMI'][i]): ptoca_seqs.append(make_cs(CS['AMI'], b'\x00\x00'))
        for _ in range(distributed_counts['DBR'][i]): ptoca_seqs.append(make_cs(CS['DBR'], b'\x00\x00\x00\x00'))
        for _ in range(distributed_counts['DIR'][i]): ptoca_seqs.append(make_cs(CS['DIR'], b'\x00\x00\x00\x00'))
        for _ in range(distributed_counts['PTOCA_NOP'][i]): ptoca_seqs.append(make_cs(CS['NOP'], b'\x00\x00'))
        for _ in range(distributed_counts['SCFL'][i]): ptoca_seqs.append(make_cs(CS['SCFL'], b'\x01'))
        sec_payload = b'\x00\x01\x00\x00\x00\x00\x08\x08\x08\x00\xFF\x00\x00\x00'
        for _ in range(distributed_counts['SEC'][i]): ptoca_seqs.append(make_cs(CS['SEC'], sec_payload))
        for _ in range(distributed_counts['SIA'][i]): ptoca_seqs.append(make_cs(CS['SIA'], b'\x00\x00\x00'))
        for _ in range(distributed_counts['STO'][i]): ptoca_seqs.append(make_cs(CS['STO'], b'\x00\x00\x2D\x00'))
        for _ in range(distributed_counts['SVI'][i]): ptoca_seqs.append(make_cs(CS['SVI'], b'\x00\x00'))
        for _ in range(distributed_counts['TRN'][i]): ptoca_seqs.append(make_cs(CS['TRN'], b'\x40'*20))

        num_ptx = distributed_counts['PTX'][i]
        if num_ptx > 0:
            seqs_per_ptx = len(ptoca_seqs) // num_ptx
            for p in range(num_ptx):
                payload = b''
                start = p * seqs_per_ptx
                end = (p+1) * seqs_per_ptx if p < num_ptx - 1 else len(ptoca_seqs)
                for s in range(start, end): payload += ptoca_seqs[s]
                while len(payload) > 30000:
                    content += make_sf(SF['PTX'], payload[:30000])
                    payload = payload[30000:]
                content += make_sf(SF['PTX'], payload)
        elif len(ptoca_seqs) > 0:
            content += make_sf(SF['PTX'], b''.join(ptoca_seqs))

        eng_sf = make_sf(SF['ENG'], b'GROUP' + f'{i:03d}'.encode())
        current_len = len(content) + len(eng_sf)
        if current_len < file_sizes[i]:
            padding_needed = file_sizes[i] - current_len
            while padding_needed > 0:
                chunk = min(padding_needed - 8, 30000)
                if chunk < 0: chunk = 0
                content += make_sf(b'\xD3\xEE\x00', b'\x00' * chunk)
                padding_needed -= (chunk + 8)
        content += eng_sf
        with open(file_path, 'wb') as f: f.write(content)
    print(f"Generated {total_files} files in {output_dir}")

if __name__ == "__main__":
    generate_testset()
