import os
import struct

def to_cp500(s):
    return s.encode('cp500')

def create_sf(sfid, payload=b''):
    length = len(payload) + 8
    return b'\x5a' + struct.pack('>H', length) + sfid + b'\x00\x00\x00' + payload

def ptoca_cs(func, payload=b'', chained=False):
    func_byte = func | (1 if chained else 0)
    length = len(payload) + 2
    return b'\x2b\xd3' + struct.pack('B', length) + struct.pack('B', func_byte) + payload

def goca_order(order, payload=b''):
    if order == 0x70: # GBSEG
        length_byte = 12
        segment_data_len = len(payload)
        # command(1), len(1), name(4), flag(1), prop(1), dlen(2), pred(4)
        return struct.pack('>BB4sBBH4s', 0x70, length_byte, b'S001', 0, 0, segment_data_len, b'P001') + payload
    elif order == 0x68: # GBAR
        return struct.pack('>BB', 0x68, 0x00)
    elif order == 0x60: # GEAR
        return struct.pack('>BB', 0x60, 0x00)
    elif order == 0x21: # GSCP
        return struct.pack('>BBhh', 0x21, 0x04, 0, 0)
    else:
        return struct.pack('>BB', order, len(payload)) + payload

def generate_synthetic_big(output_path):
    os.makedirs(os.path.dirname(output_path), exist_ok=True)

    ptoca_targets = {
        'AMB': 83851, 'AMI': 83851, 'DBR': 915, 'DIR': 7919,
        'NOP': 5907, 'SCFL': 18951, 'SEC': 10645, 'SIA': 1177,
        'STO': 2951, 'SVI': 10373, 'TRN': 75017
    }
    ptoca_counts = {k: 0 for k in ptoca_targets}
    ptoca_keys = ['AMB', 'AMI', 'DIR', 'DBR', 'NOP', 'SCFL', 'SEC', 'SIA', 'STO', 'SVI', 'TRN']
    ptoca_funcs = [0xD2, 0xC6, 0xE4, 0xE6, 0xF8, 0xF0, 0x80, 0xC2, 0xF6, 0xC4, 0xDA]

    groups = 261
    pages = 1002
    ptx_total = 5907
    gad_total = 433

    with open(output_path, 'wb') as f:
        f.write(create_sf(b'\xd3\xa8\xa8', to_cp500('SYNTHETI'))) # BDT

        page_idx = 0
        ptx_idx = 0
        gad_idx = 0

        pages_per_group = pages // groups
        extra_pages = pages % groups

        for i in range(groups):
            f.write(create_sf(b'\xd3\xa8\xad', to_cp500(f'G{i:05}'))) # BNG
            f.write(create_sf(b'\xd3\xab\xcc', to_cp500('IMM00001'))) # IMM

            num_pages = pages_per_group + (1 if i < extra_pages else 0)
            for j in range(num_pages):
                if page_idx >= pages: break
                f.write(create_sf(b'\xd3\xa8\xaf', to_cp500(f'P{page_idx:05}'))) # BPG

                # MCF Format 2
                mcf_rg1 = struct.pack('>H', 18) + b'\x0c\x02\x85\x00' + to_cp500('T1V11141') + b'\x04\x24\x02\x01'
                mcf_rg2 = struct.pack('>H', 18) + b'\x0c\x02\x85\x00' + to_cp500('T1V10500') + b'\x04\x24\x02\x02'
                f.write(create_sf(b'\xd3\xab\x8a', mcf_rg1 + mcf_rg2)) # MCF

                f.write(create_sf(b'\xd3\xa6\xaf', b'\x00' * 20)) # PGD
                f.write(create_sf(b'\xd3\xa6\x9b', b'\x00' * 10)) # PTD

                num_tle = 10494 // pages + (1 if page_idx < 10494 % pages else 0)
                for _ in range(num_tle):
                    f.write(create_sf(b'\xd3\xa0\x90', b'\x0c\x02\x0b\x00' + to_cp500('TLE NAME')))

                num_nop_sf = 11128 // pages + (1 if page_idx < 11128 % pages else 0)
                for _ in range(num_nop_sf):
                    f.write(create_sf(b'\xd3\xee\xee', b'\x00' * 10))

                num_ptx = ptx_total // pages + (1 if page_idx < ptx_total % pages else 0)
                for _ in range(num_ptx):
                    ptx_payload = b''
                    num_cs = 301557 // ptx_total + (1 if ptx_idx < 301557 % ptx_total else 0)
                    for _ in range(num_cs):
                        found = False
                        if ptoca_counts['SCFL'] < ptoca_targets['SCFL']:
                            lid = 1 if (ptoca_counts['SCFL'] % 4) != 0 else 2
                            ptx_payload += ptoca_cs(0xF0, bytes([lid]))
                            ptoca_counts['SCFL'] += 1

                        for k_idx, key in enumerate(ptoca_keys):
                            if key == 'SCFL': continue
                            if ptoca_counts[key] < ptoca_targets[key]:
                                code = ptoca_funcs[k_idx]
                                payload = b''
                                if key == 'TRN': payload = to_cp500('A' * 21)
                                elif key == 'SEC':
                                    payload = b'\x00\x01' + b'\x00' * 4 + b'\x08\x08\x08\x00' + b'\xff\x00\x00\x00'
                                elif key in ['AMB', 'AMI', 'DIR', 'DBR', 'SVI', 'SIA']: payload = b'\x00\x00'
                                elif key == 'STO': payload = b'\x00\x00\x00\x00'

                                ptx_payload += ptoca_cs(code, payload)
                                ptoca_counts[key] += 1
                                found = True
                                break
                        if not found: break
                    f.write(create_sf(b'\xd3\xee\x9b', ptx_payload))
                    ptx_idx += 1

                if gad_idx < gad_total:
                    num_gbar = 3423 // gad_total + (1 if gad_idx < 3423 % gad_total else 0)
                    num_gscp = 20177 // gad_total + (1 if gad_idx < 20177 % gad_total else 0)
                    seg_data = b''
                    for _ in range(num_gbar):
                        seg_data += goca_order(0x68) + goca_order(0x60)
                    for _ in range(num_gscp):
                        seg_data += goca_order(0x21)

                    f.write(create_sf(b'\xd3\xee\xbb', goca_order(0x70, seg_data)))
                    gad_idx += 1

                f.write(create_sf(b'\xd3\xa9\xaf', to_cp500('EPGNAMEA'))) # EPG
                page_idx += 1

            f.write(create_sf(b'\xd3\xa9\xad', to_cp500(f'G{i:05}'))) # ENG

        for _ in range(1152): f.write(create_sf(b'\xd3\xa6\xfb', b'\x01\x00\x00\x00\x00\x00\x00\x00\x00')) # IDD
        for _ in range(1152): f.write(create_sf(b'\xd3\xab\xfb', b'\x00\x00')) # MIO
        for _ in range(3485): f.write(create_sf(b'\xd3\xee\xfb', b'\x00' * 10)) # IPD
        for _ in range(1256): f.write(create_sf(b'\xd3\xa6\x6b', b'\x0c\x02\x01\x00' + b'\x00'*8)) # OBD
        for _ in range(1256): f.write(create_sf(b'\xd3\xac\x6b', b'\x01' + struct.pack('>B', 23) + b'\x00' * 22)) # OBP
        for _ in range(693): f.write(create_sf(b'\xd3\xaf\x5f', to_cp500('PSEGNAME') + b'\x00\x00\x00\x00\x00\x00')) # IPS
        for _ in range(693): f.write(create_sf(b'\xd3\xb1\x5f', struct.pack('>H', 12) + b'\x00\x00\x00\x00' + to_cp500('PSEGNAME'))) # MPS
        for _ in range(104):
            f.write(create_sf(b'\xd3\xa6\xbb', b'\x21\x04\x00\x00\x00\x0f')) # GDD

        f.write(create_sf(b'\xd3\xa9\xa8', to_cp500('SYNTHETI'))) # EDT

    print(f"Generated {output_path}")

if __name__ == "__main__":
    generate_synthetic_big("test/big/synthetic.afp")
