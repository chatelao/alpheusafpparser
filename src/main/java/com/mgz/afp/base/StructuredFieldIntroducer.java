/*
Copyright 2015 Rudolf Fiala

This file is part of Alpheus AFP Parser.

Alpheus AFP Parser is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Alpheus AFP Parser is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Alpheus AFP Parser.  If not, see <http://www.gnu.org/licenses/>
*/

package com.mgz.afp.base;

import com.mgz.afp.base.annotations.AFPField;
import com.mgz.afp.enums.SFFlag;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.EnumSet;

/**
 * <b>Structured Field Introducer (SFI)</b><br> The MO:DCA Structured Field Introducer (SFI)
 * identifies type and length of the structured field.
 */
public class StructuredFieldIntroducer {

  /**
   * SFLength[0,1].
   */
  @AFPField(isEditable = false)
  int sfLength;
  @AFPField(isEditable = false)
  SFTypeID sfTypeID;
  private byte[] rawSfTypeID;
  private int packedRawTypeID = -1;
  /**
   * FlagByte[5].
   */
  @AFPField
  EnumSet<SFFlag> flagByte;
  private int rawFlagByte = -1;
  /**
   * The reserved[6,7] (should be zero; usually used as SF counter[6,7]).
   */
  @AFPField
  int reserved = 0x0000;
  /**
   * ExtLength[8] Optional. Length of the extension including the length of ExtLength itself
   */
  @AFPField
  short extenstionLength;
  /**
   * ExtData[9-263] Optional. Contains up to 254 bytes of application-defined SFI extension data.
   * For ExtData to be valid, bit 0 of FlagByte must be B'1'.<br> A structured field introducer may
   * be extended by up to 255 bytes. The presence of an SFI extension is indicated by a value of
   * B'1' in bit 0 of the SFI flag byte. If an extension is present, the introducer is at least 8
   * bytes, but not more than 263 bytes, in length. The first byte of the extension specifies its
   * length. If an extension to the structured field introducer is present, the structured field's
   * data can occupy up to 32,759 bytes, less the length of the extension.
   */
  @AFPField(maxSize = 254)
  byte[] extenstion;
  AFPParserConfiguration actualConfig;
  @AFPField(isHidden = true)
  private long fileOffset;

  public static StructuredFieldIntroducer parse(InputStream is) throws AFPParserException {
    StructuredFieldIntroducer sfi = SfiPool.acquire();

    try {
      byte[] hdr = new byte[8];
      int totalRead = 0;
      while (totalRead < 8) {
        int r = is.read(hdr, totalRead, 8 - totalRead);
        if (r == -1) {
          break;
        }
        totalRead += r;
      }
      if (totalRead < 2) {
        throw new AFPParserException("Failed to decode SFI header.");
      }

      sfi.sfLength = ((hdr[0] & 0xFF) << 8) | (hdr[1] & 0xFF);
      if (sfi.sfLength < 8) {
        throw new AFPParserException("Invalid SF length: " + sfi.sfLength + ". Minimum length is 8.");
      }
      if (totalRead < 8) {
        throw new AFPParserException("Reached end of stream while parsing SFI header.");
      }

      int sfClass = hdr[2] & 0xFF;
      int sfType = hdr[3] & 0xFF;
      int sfCategory = hdr[4] & 0xFF;
      sfi.packedRawTypeID = (sfClass << 16) | (sfType << 8) | sfCategory;
      sfi.rawSfTypeID = null;
      sfi.sfTypeID = SFTypeID.valueOf(sfClass, sfType, sfCategory);

      sfi.rawFlagByte = hdr[5] & 0xE8;
      sfi.flagByte = null;

      sfi.reserved = ((hdr[6] & 0xFF) << 8) | (hdr[7] & 0xFF);
    } catch (IOException ioex) {
      throw new AFPParserException("Failed to decode SFI header.", ioex);
    }

    try {
      if (sfi.isFlagSet(SFFlag.hasExtension)) {
        int extLen = is.read();
        if (extLen == -1) {
          throw new AFPParserException("Reached end of stream while parsing SF extension length.");
        }
        sfi.extenstionLength = (short) extLen;
        if (sfi.extenstionLength < 1) {
          throw new AFPParserException("Invalid SF extension length: " + sfi.extenstionLength);
        }
        int extDataLen = sfi.extenstionLength - 1;
        sfi.extenstion = new byte[extDataLen];
        int totalExtRead = 0;
        while (totalExtRead < extDataLen) {
          int read = is.read(sfi.extenstion, totalExtRead, extDataLen - totalExtRead);
          if (read == -1) {
            break;
          }
          totalExtRead += read;
        }
        if (totalExtRead < extDataLen) {
          throw new AFPParserException("Failed to read SFI extension data.");
        }
      }
    } catch (IOException ioex) {
      throw new AFPParserException("Failed to decode decode SFI extension data.", ioex);
    }

    return sfi;
  }

  /**
   * Parses a {@link StructuredFieldIntroducer} from a {@link ByteBuffer} at the given offset.
   *
   * @param buffer the buffer to parse from
   * @param offset the starting offset in the buffer
   * @return the parsed {@link StructuredFieldIntroducer}
   * @throws AFPParserException if parsing fails
   */
  public static StructuredFieldIntroducer parse(ByteBuffer buffer, int offset) throws AFPParserException {
    if (offset < 0 || offset + 8 > buffer.limit()) {
      throw new AFPParserException("Not enough bytes for SF introducer at offset 0x" + Integer.toHexString(offset));
    }
    StructuredFieldIntroducer sfi = SfiPool.acquire();

    sfi.sfLength = ((buffer.get(offset) & 0xFF) << 8) | (buffer.get(offset + 1) & 0xFF);
    if (sfi.sfLength < 8) {
      throw new AFPParserException("Invalid SF length: " + sfi.sfLength + ". Minimum length is 8.");
    }
    int sfClass = buffer.get(offset + 2) & 0xFF;
    int sfType = buffer.get(offset + 3) & 0xFF;
    int sfCategory = buffer.get(offset + 4) & 0xFF;
    sfi.packedRawTypeID = (sfClass << 16) | (sfType << 8) | sfCategory;
    sfi.rawSfTypeID = null;
    sfi.sfTypeID = SFTypeID.valueOf(sfClass, sfType, sfCategory);
    sfi.rawFlagByte = buffer.get(offset + 5) & 0xE8;
    sfi.flagByte = null;
    sfi.reserved = ((buffer.get(offset + 6) & 0xFF) << 8) | (buffer.get(offset + 7) & 0xFF);

    if (sfi.isFlagSet(SFFlag.hasExtension)) {
      if (offset + 9 > buffer.limit()) {
        throw new AFPParserException("Reached end of buffer while parsing SF extension length.");
      }
      int extLen = buffer.get(offset + 8) & 0xFF;
      sfi.extenstionLength = (short) extLen;
      if (sfi.extenstionLength < 1) {
        throw new AFPParserException("Invalid SF extension length: " + sfi.extenstionLength);
      }
      int extDataLen = sfi.extenstionLength - 1;
      if (offset + 9 + extDataLen > buffer.limit()) {
        throw new AFPParserException("Failed to read SFI extension data.");
      }
      sfi.extenstion = new byte[extDataLen];
      if (extDataLen > 0) {
        buffer.get(offset + 9, sfi.extenstion, 0, extDataLen);
      }
    }

    return sfi;
  }

  /**
   * Parses a {@link StructuredFieldIntroducer} from a {@link ByteBuffer}.
   *
   * @param buffer the buffer to parse from
   * @return the parsed {@link StructuredFieldIntroducer}
   * @throws AFPParserException if parsing fails
   */
  public static StructuredFieldIntroducer parse(ByteBuffer buffer) throws AFPParserException {
    if (buffer.remaining() < 8) {
      throw new AFPParserException("Not enough bytes remaining in buffer for SF introducer.");
    }
    StructuredFieldIntroducer sfi = SfiPool.acquire();

    sfi.sfLength = ((buffer.get() & 0xFF) << 8) | (buffer.get() & 0xFF);
    if (sfi.sfLength < 8) {
      throw new AFPParserException("Invalid SF length: " + sfi.sfLength + ". Minimum length is 8.");
    }
    int sfClass = buffer.get() & 0xFF;
    int sfType = buffer.get() & 0xFF;
    int sfCategory = buffer.get() & 0xFF;
    sfi.packedRawTypeID = (sfClass << 16) | (sfType << 8) | sfCategory;
    sfi.rawSfTypeID = null;
    sfi.sfTypeID = SFTypeID.valueOf(sfClass, sfType, sfCategory);
    sfi.rawFlagByte = buffer.get() & 0xE8;
    sfi.flagByte = null;
    sfi.reserved = ((buffer.get() & 0xFF) << 8) | (buffer.get() & 0xFF);

    if (sfi.isFlagSet(SFFlag.hasExtension)) {
      if (!buffer.hasRemaining()) {
        throw new AFPParserException("Reached end of buffer while parsing SF extension length.");
      }
      int extLen = buffer.get() & 0xFF;
      sfi.extenstionLength = (short) extLen;
      if (sfi.extenstionLength < 1) {
        throw new AFPParserException("Invalid SF extension length: " + sfi.extenstionLength);
      }
      int extDataLen = sfi.extenstionLength - 1;
      if (buffer.remaining() < extDataLen) {
        throw new AFPParserException("Failed to read SFI extension data.");
      }
      sfi.extenstion = new byte[extDataLen];
      if (extDataLen > 0) {
        buffer.get(sfi.extenstion);
      }
    }

    return sfi;
  }

  public byte[] toBytes() throws IOException {
    int fbByte = resolveFlagByte();
    boolean hasExt = (fbByte & 0x80) != 0;
    int totalLen = hasExt ? 8 + extenstionLength : 8;
    byte[] out = new byte[totalLen];
    writeHeaderToArray(out, fbByte);
    if (hasExt) {
      out[8] = (byte) (extenstionLength & 0xFF);
      if (extenstion != null) {
        System.arraycopy(extenstion, 0, out, 9, Math.min(extenstion.length, totalLen - 9));
      }
    }
    return out;
  }

  /**
   * Writes the SFI directly to the given {@link OutputStream}.
   *
   * @param os the {@link OutputStream} to write to
   * @throws IOException if writing fails
   */
  public void write(OutputStream os) throws IOException {
    int fbByte = resolveFlagByte();
    byte[] hdr = new byte[8];
    writeHeaderToArray(hdr, fbByte);
    os.write(hdr);
    if ((fbByte & 0x80) != 0) {
      os.write(extenstionLength & 0xFF);
      if (extenstion != null) {
        os.write(extenstion);
      }
    }
  }

  private int resolveFlagByte() {
    if (flagByte != null) {
      return SFFlag.toByte(flagByte);
    }
    return rawFlagByte >= 0 ? rawFlagByte : 0;
  }

  private void writeHeaderToArray(byte[] hdr, int fbByte) {
    hdr[0] = (byte) ((sfLength >>> 8) & 0xFF);
    hdr[1] = (byte) (sfLength & 0xFF);
    if (sfTypeID != null && sfTypeID != SFTypeID.Undefined) {
      hdr[2] = (byte) sfTypeID.getSfClass().toByte();
      hdr[3] = (byte) sfTypeID.getSfType().toByte();
      hdr[4] = (byte) sfTypeID.getSfCategory().toByte();
    } else if (rawSfTypeID != null && rawSfTypeID.length >= 3) {
      hdr[2] = rawSfTypeID[0];
      hdr[3] = rawSfTypeID[1];
      hdr[4] = rawSfTypeID[2];
    } else if (packedRawTypeID >= 0) {
      hdr[2] = (byte) ((packedRawTypeID >>> 16) & 0xFF);
      hdr[3] = (byte) ((packedRawTypeID >>> 8) & 0xFF);
      hdr[4] = (byte) (packedRawTypeID & 0xFF);
    } else if (sfTypeID != null) {
      hdr[2] = (byte) sfTypeID.getSfClass().toByte();
      hdr[3] = (byte) sfTypeID.getSfType().toByte();
      hdr[4] = (byte) sfTypeID.getSfCategory().toByte();
    } else {
      hdr[2] = 0;
      hdr[3] = 0;
      hdr[4] = 0;
    }
    hdr[5] = (byte) (fbByte & 0xFF);
    hdr[6] = (byte) ((reserved >>> 8) & 0xFF);
    hdr[7] = (byte) (reserved & 0xFF);
  }

  private static int flagMask(SFFlag flag) {
    return switch (flag) {
      case hasExtension -> 0x80;
      case isEncrypted -> 0x40;
      case isSegmented -> 0x20;
      case isPadded -> 0x08;
    };
  }

  public int getLengthOfStructuredFieldIntroducerIncludingExtension() {
    if (isFlagSet(SFFlag.hasExtension)) {
      return 8 + extenstionLength;
    } else {
      return 8;
    }
  }

  public int getSFLength() {
    return sfLength;
  }

  public void setSFLength(int length) {
    sfLength = length;
  }

  public short getExtensionLength() {
    return extenstionLength;
  }

  public byte[] getExtensionData() {
    return extenstion;
  }

  /**
   * Sets the SFI extender Data. The corresponding {@link SFFlag#hasExtension} flag and size of
   * extender data of SFI is set accordingly. If the given extender data is null, the {@link
   * SFFlag#hasExtension} flag is revoked and size of extender data of SFI is set to zero (not
   * used).
   */
  public void setExtensionData(byte[] sfiExtensionData) {
    if (sfiExtensionData != null && sfiExtensionData.length > 254) {
      byte[] tmp = new byte[254];
      System.arraycopy(sfiExtensionData, 0, tmp, 0, 254);
      sfiExtensionData = tmp;
    }
    extenstion = sfiExtensionData;
    if (sfiExtensionData != null) {
      setFlag(SFFlag.hasExtension);
      extenstionLength = (short) (sfiExtensionData.length + 1);
    } else {
      removeFlag(SFFlag.hasExtension);
      extenstionLength = (short) 0;
    }
  }

  public int getReserved() {
    return reserved;
  }

  public void setReserved(int reserved) {
    this.reserved = reserved;
  }

  public boolean isFlagSet(SFFlag flag) {
    if (flag == null) {
      return false;
    }
    if (this.flagByte != null) {
      return flagByte.contains(flag);
    }
    return rawFlagByte >= 0 && (rawFlagByte & flagMask(flag)) != 0;
  }

  public void setFlag(SFFlag flag) {
    if (flag == null) {
      return;
    }
    if (this.flagByte != null) {
      flagByte.add(flag);
    } else if (rawFlagByte >= 0) {
      rawFlagByte |= flagMask(flag);
    } else {
      rawFlagByte = flagMask(flag);
    }
  }

  public void removeFlag(SFFlag flag) {
    if (flag == null) {
      return;
    }
    if (flagByte != null) {
      flagByte.remove(flag);
    } else if (rawFlagByte >= 0) {
      rawFlagByte &= ~flagMask(flag);
    }
  }

  public SFTypeID getSFTypeID() {
    return sfTypeID;
  }

  public void setSFTypeID(SFTypeID sfTypeID) {
    this.sfTypeID = sfTypeID;
  }

  public byte[] getRawSfTypeID() {
    if (rawSfTypeID == null && packedRawTypeID >= 0) {
      rawSfTypeID = new byte[] {
          (byte) ((packedRawTypeID >>> 16) & 0xFF),
          (byte) ((packedRawTypeID >>> 8) & 0xFF),
          (byte) (packedRawTypeID & 0xFF)
      };
    }
    return rawSfTypeID;
  }

  public void setRawSfTypeID(byte[] rawSfTypeID) {
    this.rawSfTypeID = rawSfTypeID;
    this.packedRawTypeID = -1;
  }

  /**
   * Returns the position in the file where the structured field begins. A structured field begins
   * with the 0x5A byte.
   */
  public long getFileOffset() {
    return fileOffset;
  }

  public void setFileOffset(long fileOffset) {
    this.fileOffset = fileOffset;
  }

  public EnumSet<SFFlag> getFlagByte() {
    if (flagByte == null && rawFlagByte >= 0) {
      flagByte = SFFlag.valueOf(rawFlagByte);
      rawFlagByte = -1;
    }
    return flagByte;
  }

  public void setFlagByte(EnumSet<SFFlag> flagByte) {
    this.flagByte = flagByte;
    this.rawFlagByte = -1;
  }

  public AFPParserConfiguration getActualConfig() {
    return actualConfig;
  }

  public void setActualConfig(AFPParserConfiguration actualConfig) {
    this.actualConfig = actualConfig;
  }

  protected boolean isPooled = true;

  public boolean isPooled() {
    return isPooled;
  }

  public void setPooled(boolean isPooled) {
    this.isPooled = isPooled;
  }

  /**
   * Resets all fields to their default values for reuse in an object pool.
   */
  public void reset() {
    sfLength = 0;
    sfTypeID = null;
    rawSfTypeID = null;
    packedRawTypeID = -1;
    flagByte = null;
    rawFlagByte = -1;
    reserved = 0x0000;
    extenstionLength = 0;
    extenstion = null;
    actualConfig = null;
    fileOffset = 0;
    isPooled = true;
  }

  @Override
  public String toString() {
    return "StructuredFieldIntroducer{" +
        "sfTypeID=" + sfTypeID +
        '}';
  }
}
