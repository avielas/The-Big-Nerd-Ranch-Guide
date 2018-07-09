package com.bignerdranch.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Map<UUID,Crime> mCrimes;

    private CrimeLab(Context context){
        mCrimes = new LinkedHashMap<>();
        for(int i = 0; i < 100; i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            crime.setRequiresPolice(i % 2 == 0);
            mCrimes.put(crime.getId(),crime);
        }
    }

    public static CrimeLab get(Context context){
        if(null == sCrimeLab)
        {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public List<Crime> getCrimes() {
        return new ArrayList<>(mCrimes.values());
    }

    public void setCrimes(Map<UUID,Crime> crimes) {
        mCrimes = crimes;
    }

    public Crime getCrime(UUID uuid){

        if(mCrimes.containsKey(uuid))
            return mCrimes.get(uuid);

        return null;
    }
}
