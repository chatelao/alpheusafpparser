# Chapter 1. Introduction
Programmers can develop applications for Advanced Function Presentation (AFP) hardware and software,
generating either traditional unformatted line data, fully composed Mixed Object Document Content
Architecture (MO:DCA) data (also called AFP data), or a combination of both. This book contains examples
and suggestions for writing such applications. AFP line data and
MO:DCA data streams are supported, for
example, in the following environments:
• Advanced Interactive Executive (AIX ®)
• Application System/400 (AS/400), iSeries, and System i5 ®
• Operating System/2 ® (OS/2)
• IBM mainframe environments, including:
– OS/390® and z/OS®
– Virtual Machine (VM) and z/VM ®
– Virtual Storage Extended (VSE) and z/VSE ®
• Linux™
• Microsoft ® Windows®
The print data streams can include text, images, graphics, and bar code in MO:DCA format. The MO:DCA
architecture defines the data stream used by applications to describe documents and object envelopes for
interchange with other applications and application services. Documents defined in the MO:DCA format can be
archived in a data base, then later retrieved, viewed, and printed in local or distributed systems environments.
Presentation services programs in the zSeries environments accept data in traditional line-printer format and
generate page-mode output from the line data, using information contained in a Page Definition (PageDef)
resource object. The line data mapped by the Page Definition might or might not include additional MO:DCA
structured fields. A file that includes a combination of line data and MO:DCA structured fields is called a mixedmode file. Only certain MO:DCA structured fields can be intermixed with line data. Detailed information on
coding those structured fields appears in Chapter 4, “Mixed Documents: Adding MO:DCA Structured Fields to
Line Data”, on page 39.
Presentation services programs in the AIX and Windows environments accept non-MO:DCA data streams that
can be formatted using Page Definition resource objects. These data streams can be in any of the following
formats:
• Traditional line printer format, also called 1403 format
• Unformatted ASCII files without escape sequences
• DBCS (double-byte character set) ASCII files generated for an IBM 5577 or 5587
Presentation services programs in the IBM i environment accept line data or mixed data, either created on a
zSeries platform and networked to an IBM i environment or created natively on an IBM i environment . Such
data is placed on the printer spool using a Printer File, which may also specify the Page Definition and Form
Definition (Formdef) to be used for formatting and printing the data.


## Related Architectures
Mixed-mode data streams can include line data, MO:DCA structured fields, and objects of the following types:
• Bar Code Object Content Architecture™ (BCOCA)
• Color Management Object Content Architecture (CMOCA)
• Font Object Content Architecture (FOCA)
• Graphics Object Content Architecture (GOCA)
• Image Object Content Architecture (IOCA)
• Presentation T ext Object Content Architecture (PTOCA)
• Non-OCA paginated presentation objects such as Encapsulated PostScript ® (EPS)
A related architecture, but not a user programming language, is the Intelligent Printer Data Stream™ (IPDS™)
architecture. This is the data stream architecture used by print server products to manage IPDS printers.
## System Model
AFP print servers provide support for interpreting line data, mixed-mode data, and MO:DCA data, for resolving
resource references, and for building printer data streams for driving IPDS printers. Figure 1 shows the general
relationship between the AFP data streams, the print server products, and IPDS printers.
Figure 1. AFP System Printing Relationships
AFP
Data
Stream
Print
File
Page
Deﬁnitions
Form
Deﬁnitions
Page
Segments
Overlays
Fonts
Intelligent
Printer
Data Stream
(IPDS)
Page
Printer
AFP
Print
Services
System
Commands
Print Control
Each print server product has its own books that describe how to submit print jobs in its system environment.
See these books for information on setting up jobs for printing.


## Supported Environments
Presentation services programs provide common printer support and print services in the following
environments:
• AIX
• AS/400 and System i5
• OS/2
• OS/390 and z/OS
• VM and z/VM
• VSE and z/VSE
• Linux
• Windows




