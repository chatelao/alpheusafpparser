# Shallow Structured Field Implementations Report

This report lists all Structured Field classes that do not provide their own payload parsing logic (shallow implementations).

**Total Shallow Fields Identified: 71**

## Package: base
- StructuredFieldErrornouslyBuilt

## Package: bcoca
- BBC_BeginBarCodeObject
- EBC_EndBarCodeObject

## Package: cmoca
- CMR_ColorManagementResource

## Package: foca
- BCF_BeginCodedFont
- BCP_BeginCodePage
- BFN_BeginFont
- ECF_EndCodedFont
- ECP_EndCodePage
- EFN_EndFont
- FNG_FontPatterns
- FNN_FontNameMap

## Package: goca
- BGR_BeginGraphicsObject
- EGR_EndGraphicsObject
- GAD_GraphicsData (Minimal/Empty override)

## Package: lineData
- BDX_BeginDataMapTransmitionSubcase
- BPM_BeginPageMap
- DXD_DataMapTransmitionSubcaseDescriptor
- EDM_EndDataMap
- EDX_EndDataMapTransmitionSubcase
- EPM_EndPageMap
- IDM_InvokeDataMap

## Package: modca
- BAG_BeginActiveEnvironmentGroup
- BDG_BeginDocumentEnvironmentGroup
- BDI_BeginDocumentIndex
- BFG_BeginFormEnvironmentGroup
- BFM_BeginFormMap
- BII_BeginIMImageObject
- BIM_BeginImageObject
- BMM_BeginMediumMap
- BMO_BeginOverlay
- BNG_BeginNamedPageGroup
- BOC_BeginObjectContainer
- BOG_BeginObjectEnvironmentGroup
- BPF_BeginPrintFile
- BPG_BeginPage
- BPS_BeginPageSegment
- BRG_BeginResourceGroup
- BSG_BeginResourceEnvironmentGroup
- EAG_EndActiveEnvironmentGroup
- EDG_EndDocumentEnvironmentGroup
- EDI_EndDocumentIndex
- EDT_EndDocument
- EFG_EndFormEnvironmentGroup
- EFM_EndFormMap
- EII_EndIMImageObject
- EIM_EndImageObject
- EMM_EndMediumMap
- EMO_EndOverlay
- ENG_EndNamedPageGroup
- EOC_EndObjectContainer
- EOG_EndObjectEnvironmentGroup
- EPF_EndPrintFile
- EPG_EndPage
- EPS_EndPageSegment
- ERG_EndResourceGroup
- ERS_EndResource
- ESG_EndResourceEnvironmentGroup
- IEL_IndexElement
- IMM_InvokeMediumMap
- IRD_IMImageRasterData
- NOP_NoOperation
- OBD_ObjectAreaDescriptor
- OCD_ObjectContainerData
- PEC_PresentationEnvironmentControl
- TLE_TagLogicalElement

## Package: modca_L
- BCA_BeginColorAttributeTable
- CAT_ColorAttributeTable (Minimal/Empty override)
- ECA_EndColorAttributeTable

## Package: ptoca
- BPT_BeginPresentationTextObject
- EPT_EndPresentationTextObject
