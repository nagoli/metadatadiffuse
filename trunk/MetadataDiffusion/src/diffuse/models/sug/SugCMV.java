/*
 *MetadataDiffusion
Copyright (C) Olivier Motelet.

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package diffuse.models.sug;

import diffuse.metadata.MetadataSetValue;
import diffuse.propagation.CMV;


/**
 * the CMV for sugestion associate a value to a certain weight
 * The weight is between 0 and 1
 * 
 * @author omotelet
 *
 */


public class SugCMV extends CMV {

	
	static double ITSMinValue = 0.01;
	
	double itsWeight; 
	MetadataSetValue itsValue;
	
	public SugCMV(MetadataSetValue aValue, double aWeight) {
		super(); 
		itsValue = aValue;
		itsWeight = aWeight;
	}
	
	public SugCMV(SugCMV aSugCMV) {
		super(aSugCMV);
		itsValue = aSugCMV.itsValue;
		itsWeight = aSugCMV.itsWeight;
			
	}
	
	/**
	 * add a factor to itsWeight (should be between 0 and 1)
	 * set the weight to 0 if its inferior to ITSMinValue
	 * @param aFactor
	 */
	public void addFactorToWeight(double aFactor){
		itsWeight = itsWeight*aFactor;
		if (itsWeight<ITSMinValue)
			itsWeight=0;
	}
	
	@Override
	public boolean isNeutral() {
		return itsWeight==0;
	}
	
	/**
	 * compare the weights
	 */
	public int compareTo(CMV aCMV) {
		if (aCMV instanceof SugCMV) {
			SugCMV theSugCMV = (SugCMV) aCMV;
			if (itsValue.equals(theSugCMV.itsValue))
				if (itsWeight>=theSugCMV.itsWeight) return 1;
				else return -1;
		}
		System.out.println("I tried to compare sugCMV that are not comparable");
		return 0;
	}
	
	@Override
	public boolean equals(Object aObj) {
		if (aObj instanceof SugCMV) {
			SugCMV theSugCMV = (SugCMV) aObj;
			return itsValue.equals(theSugCMV.itsValue) && equals(itsWeight,theSugCMV.itsWeight);
		}
		return false;
	}
	
	/**
	 * Precision off 0.0001 comparaison of double
	 *
	 */
	public boolean equals(double aD, double aD2 ){
		return  (Math.abs(aD - aD2) < 0.00001) ;
			
	}
	
	@Override
	public String toString() {
		return itsValue+"("+itsWeight+")";
	}
	
	@Override
	public SugCMV clone() {
		return new SugCMV(this);
	}
	

	public double getWeight() {
		return itsWeight;
	}

	public MetadataSetValue getValue() {
		return itsValue;
	}
	
	@Override
	public CMV union(CMV aCMV) {
		System.out.println("Union is not implemented for SugCMV");
		return null;
	}
	

	@Override
	public CMV intersection(CMV aCMV) {
		System.out.println("Intersection is not implemented for SugCMV");
		return null;
	}
	
}
