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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import diffuse.metadata.MetadataSet;
import diffuse.metadata.MetadataSetRelationType;
import diffuse.util.fibheapMax.DynamicSetElement;


/**
 * A CMVUpdater encapsulate the update process for cmvs it keeps a trace of the
 * neighbors CMV to avoid side effect (see implementation section in chapter 5
 * of the thesis)
 * 
 */
public abstract class CMVUpdater implements DynamicSetElement {

	/**
	 * keep a cache of the CMV associated to each neighboring MetadataSet.
	 */
	protected Map<MetadataSet, CMV> itsMetadataSetToCMVMap = new LinkedHashMap<MetadataSet, CMV>();

	/**
	 * the resulting CMVs
	 */
	protected CMV itsCMV;
	protected CMV itsCMVWithoutOriginal; // CMV without the weight of original values
	

	/**
	 * the associated MetadataSet
	 */
	protected MetadataSet itsMetadataSet;
	
	protected boolean isOriginalValue=false; 
 
	/**
	 * 
	 * 
	 * @param aMetadataSet
	 */
	public CMVUpdater(MetadataSet aMetadataSet) {
		itsMetadataSet = aMetadataSet;
	}

	/**
	 * init itsCMV to the original values
	 */
	public void init(){
		itsCMV = getCMVFromOriginalValue();
	}
	
	/**
	 * WARNING the max should be consider as the min
	 * 
	 */
	public int compareTo(Object aE) {
		if (aE instanceof CMVUpdater) {
			CMVUpdater theUpdater = (CMVUpdater) aE;
			return itsCMV.compareTo(theUpdater.itsCMV);
		}
		return itsCMV.compareTo((CMV)aE);
	}

	public Comparable getKey() {
		return getDiffusion();
	}

	public void setKey(Comparable aKey) {
		//fibheap should not be responsible for updating the key this should have been done before 
	}

	/**
	 * return the adapted CMV from the original MetadataSet Value of this
	 * MetadataSet
	 * 
	 * @return
	 */
	public abstract CMV getCMVFromOriginalValue();

	
	
	
	
	
	
	
	/**
	 * make the union of a list of CMVs. It results in a new CMV;
	 * should manage the case when one of null parameters
	 * 
	 * the fusion is the operator that relate the existing CMV with new CMV values
	 * @param aCMVList
	 * @return
	 */
	public abstract CMV fusion(CMV aFirstCMV, CMV aSecondCMV);

	/**
	 * apply the rule associated to aRelationType on a list of CMVs
	 * 
	 * @param aRelation
	 * @param aCMVList
	 * @return
	 */
	public abstract CMV applyRule(MetadataSetRelationType aRelationType,
			List<CMV> aCMVList);

	/**
	 * this value is the value to be diffused to the neighboring LOs
	 * @see addAll.
	 * 
	 * @return
	 */
	public CMV getDiffusion() {
		if (itsCMV == null)
			computeCMV();
		return itsCMV;
	}


	/**
	 *  this value correspond to the diffused value of neighboring LOs
	 *  before including the effect of its original values
	 * 
	 * @see addAll.
	 * 
	 * @return
	 */
	public CMV getDiffusionWithoutOriginal() {
		if (itsCMVWithoutOriginal == null)
			computeCMV();
		return itsCMVWithoutOriginal;
	}
	
	
	
	/**
	 * add the diffusion for aUpdatedMetadataSet return true if the operation
	 * has modified the current CMV else return false.
	 * 
	 * @param aMetadataSet
	 * @param aValue
	 * @param aRelationType
	 * 
	 * @return
	 */
	public boolean updateCMV(MetadataSet aUpdatedMetadataSet, CMV aNewCMV) {
		itsMetadataSetToCMVMap.put(aUpdatedMetadataSet, aNewCMV);
		/*
		 *  //this optimization does not work with the update of relations and 
		 * if (itsCMV != null)
			if (CMVUtils.isFirstCMVMax(itsCMV,aNewCMV)
					&& !itsCMV.getModifierSet().contains(aUpdatedMetadataSet) )
					//&& ( !itsCMVWithoutOriginal.getModifierSet().contains(aUpdatedMetadataSet))
				return false; // a smaller link cannot modified this CMV
								// unless it is the origin of the change
		*/
		return computeCMV();
	}

	/**
	 * should be call when a node original values have been modified.
	 */
	public boolean updateCMV() {
		return computeCMV();
	}

	/**
	 * compute the CMV. The CMV of the MetadataSet being part of the modifier
	 * 
	 * @return
	 */
	private boolean computeCMV() {
		
		CMV theOriginalValue = getCMVFromOriginalValue();
		CMV theNewResultingCMV = null;

		Map<? extends MetadataSetRelationType, ? extends Set<? extends MetadataSet>> theRelationTypeMap = itsMetadataSet
				.getRelationTypeMap();
		if (theRelationTypeMap != null) {
			for (MetadataSetRelationType theRelationType : theRelationTypeMap
					.keySet()) {
				List<CMV> theUsableCMVs = new ArrayList<CMV>();
				for (MetadataSet theRelatedMetadataSet : theRelationTypeMap
						.get(theRelationType)) {
					CMV theRelatedCMV = itsMetadataSetToCMVMap
							.get(theRelatedMetadataSet);
					if (theRelatedCMV != null
							&& !theRelatedCMV.getModifierSet().contains(
									itsMetadataSet) && !theRelatedCMV.isNeutral())
						theUsableCMVs.add(theRelatedCMV);
				}
				if (theUsableCMVs.size() != 0)
					theNewResultingCMV = fusion(theNewResultingCMV, applyRule(
							theRelationType, theUsableCMVs));
			}
		}
		if (theNewResultingCMV!=null)
			itsCMVWithoutOriginal = theNewResultingCMV.clone();
		else itsCMVWithoutOriginal=null;
		theNewResultingCMV = fusion(theNewResultingCMV,theOriginalValue);
		if (theOriginalValue.equals(theNewResultingCMV)){
			//force theOriginalValue to be taken into account over the others
			theNewResultingCMV = theOriginalValue;
			isOriginalValue = true;
		}
		else {
			//clone the new value to avoid side effect 
			//(theOriginalValue was freshly created thus there is no risk of side effect) 
			theNewResultingCMV = theNewResultingCMV.clone();
			isOriginalValue=false;
		}
		theNewResultingCMV.addToModifierSet(itsMetadataSet);
		
		if (!theNewResultingCMV.equals(itsCMV)) {
			itsCMV = theNewResultingCMV;
			return true;
		}
		itsCMV = theNewResultingCMV;
		return false;

	}

	/**
	 * return true if the current diffusion value is equal to the original value
	 * @return
	 */
	public boolean isOriginalValue() {
		return isOriginalValue;
	}
	
	
}
