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
package diffuse.propagation;

import java.util.HashSet;
import java.util.Set;

import diffuse.metadata.MetadataSet;



/**
 * astract class  for CMV values
 * extends dynamicSetElements
 * Warning: max between to elements must be min for implementation purpose...
 * it implements the notion of modifierset, i.e., the set of metadataset participating in the modification of this value
 *
 */



public abstract class CMV implements Comparable<CMV>{
 
	
	private Set<MetadataSet> itsModifierMetadataSet;	
		
	
	public CMV() {
		itsModifierMetadataSet = new HashSet<MetadataSet>();
	}	
	
	static int nb=0;
	public CMV( CMV aCMV) {
		itsModifierMetadataSet = new HashSet<MetadataSet>(aCMV.itsModifierMetadataSet);
		//System.out.println("creation:"+ nb++);
	}	
	
	
	public  abstract boolean equals(Object aObj);
		
	
	public abstract CMV union(CMV aCMV);
	
	public abstract CMV intersection(CMV aCMV);
		
	public Set<MetadataSet> getModifierSet(){
		 	return itsModifierMetadataSet;
	}
	 
/*	public void setModifierSet(Set<MetadataSet> aSet, MetadataSet aMetadataSet){
		if (aSet == null) itsModifierMetadataSet = new HashSet<MetadataSet>();
		else itsModifierMetadataSet = new HashSet<MetadataSet>(aSet);
		itsModifierMetadataSet.add(aMetadataSet);
	}
*/
	//static int nbAdd=0;
	public void addToModifierSet(MetadataSet aMetadataSet){
		itsModifierMetadataSet.add(aMetadataSet);
		//System.out.println("addition:"+ nbAdd++);
	}
 
	public void addToModifierSet(Set<MetadataSet> aSet){
		itsModifierMetadataSet.addAll(aSet);
	}
	
	
	public abstract CMV clone();
 
	/**
	 * return true if this value cannot propagate change during a global update
	 * @return
	 */
	public abstract boolean isNeutral();
	
	
}
