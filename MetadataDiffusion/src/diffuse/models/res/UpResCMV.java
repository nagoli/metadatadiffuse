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

public class UpResCMV extends ResCMV {

	public UpResCMV(MetadataSetValue aValue,int aNature) {
		super(aValue, aNature); 
	}
	
	public UpResCMV(UpResCMV aResCMV) {
		super(aResCMV);
	}
	
	
	@Override
	public UpResCMV clone() {
		return new UpResCMV(this);
	}

	/**
	 * the min is max for upres
	 * appart for notdefined....
	 * **/
	@Override
	public int compareTo(CMV aCMV) {
		if (isNeutral()) return -1;
		if (aCMV.isNeutral()) return 1;
		if (aCMV instanceof UpResCMV) {
			UpResCMV theResCMV = (UpResCMV) aCMV;
			return -1*itsValue.compareTo(theResCMV.itsValue);
		}
		return 0;
	}

	@Override
	public CMV union(CMV aCMV) {
		if (aCMV instanceof UpResCMV) {
			UpResCMV theCMV = (UpResCMV) aCMV;
			UpResCMV theNewCMV = new UpResCMV(itsValue.union(theCMV.itsValue),itsNature);
			theNewCMV.addToModifierSet(this.getModifierSet());
			theNewCMV.addToModifierSet(theCMV.getModifierSet());
			return theNewCMV;
		}
		return this;
	}
	
	
	@Override
	public CMV intersection(CMV aCMV) {
		if (aCMV instanceof UpResCMV) {
			UpResCMV theCMV = (UpResCMV) aCMV;
			UpResCMV theNewCMV = new UpResCMV(itsValue.intersection(theCMV.itsValue),itsNature);
			theNewCMV.addToModifierSet(this.getModifierSet());
			theNewCMV.addToModifierSet(theCMV.getModifierSet());
			return theNewCMV;
		}
		return this;
	}
	
	@Override
	public boolean comply(MetadataSetValue aValue) {
		if (itsNature == ResRule.CONTAINED) return itsValue.contains(aValue);
		if (itsNature == ResRule.INFEQ) return aValue.compareTo(itsValue)<=0;
		return false;
	}
	
	
	@Override
	public String prettyPrint() {
		String thePrint="";
		if (itsNature == ResRule.CONTAINED) thePrint+="contained in {";
		if (itsNature == ResRule.INFEQ) thePrint+="<= ";
		thePrint+= itsValue.getLabel();
		if (itsNature == ResRule.CONTAINED) thePrint+="}";
		return thePrint;
	}
}
