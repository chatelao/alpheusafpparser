# Chapter 6. Compliance

This chapter describes the MOCA subsets that are supported in the MOCA architecture.

## MOCA Subsets

MOCA subsets are used to identify a specific level of MOCA functionality. Each new (higher level) subset must incorporate the complete functionality of the previous (lower level) subset. The naming of MOCA subsets is defined as follows.

Each new MOCA subset will be identified by the term **MSx**, where:
* **MS** = Metadata Subset
* **x** = level, starting with 1

## MS1 Subset

The MS1 Subset is the level of MOCA compliance required to support the functionality contained in the first edition of the MOCA Reference.

The following value of **MOType** must be supported:
* DES

The following value of **MOFormat** must be supported:
* XMP

The following values of **MOCompression** must be supported:
* NONE
* GZIP
* EXI

In environments where exception conditions can be reported, the following exception conditions must be supported:
* EC-0100
* EC-0200
* EC-0210
* EC-0220
* EC-0230
* EC-0240
* EC-0250
* EC-0300

---

# Notices
The AFP Consortium or consortium member companies might have patents or pending patent applications covering subject matter described in this document. The furnishing of this document does not give you any license to these patents.

The following statement does not apply to the United Kingdom or any other country where such provisions are inconsistent with local law: **AFP Consortium PROVIDES THIS PUBLICATION “AS IS” WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF NON-INFRINGEMENT, MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.** Some states do not allow disclaimer of express or implied warranties in certain transactions, therefore, this statement might not apply to you.

This publication could include technical inaccuracies or typographical errors. Changes are periodically made to the information herein; these changes will be incorporated in new editions of the publication. The AFP Consortium might make improvements and/or changes in the architecture described in this publication at any time without notice.

Any references in this publication to Web sites are provided for convenience only and do not in any manner serve as an endorsement of those Web sites. The materials at those Web sites are not part of the materials for this architecture and use of those Web sites is at your own risk.

The AFP Consortium may use or distribute any information you supply in any way it believes appropriate without incurring any obligation to you.

This information contains examples of data and reports used in daily business operations. To illustrate them in a complete manner, some examples include the names of individuals, companies, brands, or products. These names are fictitious and any similarity to the names and addresses used by an actual business enterprise is entirely coincidental.

# Trademarks
PostScript and XMP are either registered trademarks or trademarks of Adobe Systems Incorporated in the United States and/or other countries.

AFPC and AFP Consortium are trademarks of the AFP Consortium.

IBM is a trademark of the International Business Machines Corporation in the United States, other countries, or both.

These terms are trademarks or registered trademarks of Ricoh Co., Ltd., in the United States, other countries, or both:
* ACMA
* Advanced Function Presentation
* AFP
* AFPCC
* AFP Color Consortium
* AFP Color Management Architecture
* Bar Code Object Content Architecture
* BCOCA
* CMOCA
* Color Management Object Content Architecture
* InfoPrint
* Intelligent Printer Data Stream
* IPDS
* Mixed Object Document Content Architecture
* MO:DCA
* Ricoh

Other company, product, or service names might be trademarks or service marks of others.
