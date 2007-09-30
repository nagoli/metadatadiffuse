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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import diffuse.metadata.MetadataSet;
import diffuse.metadata.MetadataSetAttribute;
import diffuse.metadata.MetadataSetValue;
import diffuse.propagation.CMVUpdater;



/**
 * this class is responsible for storing the sugCMVholders. 
 * 
 * SugCMV are associated with a unique value. 
 * In order to get all the SugCMV for a value use getCMVCollectionFor
 * 
 * In order to have all the SugCMVHolder for a certain attribute (for diffusion purposes)
 * use getCMVHolderCollectionForUpdate
 * 
 * 
 * @author omotelet
 *
 */

public class SugCMVHolderStorage {

	Map<MetadataSetAttribute,List<SugCMVHolder>> itsCMVHolders = new HashMap<MetadataSetAttribute, List<SugCMVHolder>>();
	Map<MetadataSetAttribute,Set<MetadataSetValue>> itsRegisteredValues = new HashMap<MetadataSetAttribute, Set<MetadataSetValue>>();

	
	
	static SugCMVHolderStorage ITSInstance;
	
	public static SugCMVHolderStorage getInstance(){
		if (ITSInstance == null)
			ITSInstance = new SugCMVHolderStorage();
		return ITSInstance;
	}
	
	public SugCMVHolderStorage() {
		// TODO Auto-generated constructor stub
	}
	
	public Set<MetadataSetValue> getMetadataSetValueCollectionFor(MetadataSetAttribute aAttribute){
		if (!itsRegisteredValues.containsKey(aAttribute)){
			itsRegisteredValues.put(aAttribute,new HashSet<MetadataSetValue>());
		}
		return itsRegisteredValues.get(aAttribute);
	}
	
	public List<SugCMVHolder> getSugCMVHolderCollectionFor(MetadataSetAttribute aAttribute){
		if (!itsCMVHolders.containsKey(aAttribute)){
			itsCMVHolders.put(aAttribute,new ArrayList<SugCMVHolder>());
		}
		return itsCMVHolders.get(aAttribute);
	}
	
	
	/**
	 * return the CMVHolder for attribute. Take the metadataset to update in order to consider the new metadataValues
	 * @param aAttribute
	 * @param someModifiedMetadata
	 * @return
	 */
	public  List<SugCMVHolder> getCMVHolderCollectionForUpdate(MetadataSetAttribute aAttribute, Collection<? extends MetadataSet> someModifiedMetadata){
		for (MetadataSet theMetadataSet : someModifiedMetadata) {
			MetadataSetValue theValueIn = aAttribute.getValueIn(theMetadataSet);
			if (theValueIn==null || theValueIn.isUndefinedValue()) continue; //do not diffuse undefined value.
			Collection<MetadataSetValue> theAtomicValues = theValueIn.getAtomicValues();
			for (MetadataSetValue theMetadataSetValue : theAtomicValues)  {
				if (theMetadataSetValue==null || theMetadataSetValue.isUndefinedValue()) continue;
				Set<MetadataSetValue> theMetadataSetValueCollection = getMetadataSetValueCollectionFor(aAttribute);
				if (!theMetadataSetValueCollection.contains(theMetadataSetValue)){
					theMetadataSetValueCollection.add(theMetadataSetValue);
					getSugCMVHolderCollectionFor(aAttribute).add(new SugCMVHolder(aAttribute,theMetadataSetValue));
				}
			}
		}
		return getSugCMVHolderCollectionFor(aAttribute);
	}
	
	/**
	 * return the collection of CMV for aAttribute and aMetadataSet
	 * @param aAttribute
	 * @param aMetadataSet
	 * @return
	 */
	public  List<SugCMV> getCMVCollectionFor(MetadataSetAttribute aAttribute, MetadataSet aMetadataSet){
		if (aMetadataSet==null) return null;
		List<SugCMV> theList = new ArrayList<SugCMV>();
		for (SugCMVHolder theHolder : getSugCMVHolderCollectionFor(aAttribute)) {
			theList.add((SugCMV)theHolder.getUpdater(aMetadataSet).getDiffusion());
		} 
		return theList;
	}
	
	/**
	 * return the collection of CMV without original for aAttribute and aMetadataSet
	 * @param aAttribute
	 * @param aMetadataSet
	 * @return
	 */
	public  List<SugCMV> getCMVCollectionWithoutOriginalFor(MetadataSetAttribute aAttribute, MetadataSet aMetadataSet){
		if (aMetadataSet==null) return null;
		List<SugCMV> theList = new ArrayList<SugCMV>();
		for (SugCMVHolder theHolder : getSugCMVHolderCollectionFor(aAttribute)) {
			CMVUpdater theUpdater = theHolder.getUpdater(aMetadataSet);
			SugCMV theValue = (SugCMV)theUpdater.getDiffusion();
			if (!theUpdater.isOriginalValue() && theValue != null && !theValue.isNeutral())
				theList.add(theValue);
		} 
		return theList;
	}
	
	
}
