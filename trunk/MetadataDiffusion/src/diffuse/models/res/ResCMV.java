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
package diffuse.models.res;

import diffuse.metadata.MetadataSetValue;
import diffuse.propagation.CMV;
import diffuse.models.res.rules.ResRule;


/**
 * the CMV for restriction   is an interval boundary
 * 
 * @author omotelet
 *
 */


public abstract class ResCMV extends CMV {

	
	MetadataSetValue itsValue;
	int itsNature;
	
	public ResCMV(MetadataSetValue aValue, int aNature) {
		super(); 
		itsValue = aValue;
		itsNature = aNature;
	}
	
	public ResCMV(ResCMV aResCMV) {
		super(aResCMV);
		itsValue = aResCMV.itsValue;
		itsNature = aResCMV.itsNature;
	}
	
	
	/**
	 * considered aResCMV neutral if both lower and upper bounds are undifined
	 */
	@Override
	public boolean isNeutral() {
		return itsValue == null || itsValue.isUndefinedValue();
	}
	
	public boolean isSingleElement() {
		return itsNature==ResRule.INFEQ || itsNature == ResRule.SUPEQ;
	}
	
	public boolean isElementSet() {
		return itsNature==ResRule.CONTAINS || itsNature == ResRule.CONTAINED;
	}
	
	public ResCMV setNature(int aNature) {
		itsNature = aNature;
		return this;
	}
	
	public abstract int compareTo(CMV aCMV) ;
	
	@Override
	public boolean equals(Object aObj) {
		if (aObj instanceof ResCMV) {
			ResCMV theResCMV = (ResCMV) aObj;
			return itsValue.equals(theResCMV.itsValue);
		}
		return false;
	}
	
	public abstract boolean comply(MetadataSetValue aValue);
	
	public abstract String prettyPrint();
	
	@Override
	public String toString() {
		return itsValue.toString()+getModifierSet();
	}
	
	@Override
	public abstract ResCMV clone() ;
	

	
	public MetadataSetValue getValue() {
		return itsValue;
	}
	
}
