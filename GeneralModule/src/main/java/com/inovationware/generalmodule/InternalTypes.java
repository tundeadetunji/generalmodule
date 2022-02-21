package com.inovationware.generalmodule;

public class InternalTypes {

    public enum StoreInformation{
        Locally, Online, BothLocallyAndOnline
    }

    public enum InformationIsStored{
        OneOff, Incrementally
    }

    public enum InformationIsRetrieved{
        Locally, Online
    }

    public enum SideToReturn{
        Left, Middle, Right, AsArray, AsListOfString, AsListToString
    }

    public enum TextCase{
        Capitalize, UpperCase, LowerCase, None
    }

    public enum FormatFor{
        Web, JavaScript, Custom
    }

    public enum OrderBy{
        DESC, ASC
    }




}
