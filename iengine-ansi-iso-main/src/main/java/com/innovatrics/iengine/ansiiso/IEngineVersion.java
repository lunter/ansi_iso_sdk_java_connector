package com.innovatrics.iengine.ansiiso;

import com.sun.jna.Structure;

public class IEngineVersion extends Structure {

    {
        setFieldOrder(new String[] { "Major", "Minor" });
    }
    
    public int Major;
    public int Minor;

    @Override
    public String toString() {
        return Major + "." + Minor;
    }
}
