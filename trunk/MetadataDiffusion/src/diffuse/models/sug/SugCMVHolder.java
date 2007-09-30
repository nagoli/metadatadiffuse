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

import diffuse.metadata.MetadataSet;
import diffuse.metadata.MetadataSetAttribute;
import diffuse.metadata.MetadataSetValue;
import diffuse.propagation.CMVHolder;
import diffuse.propagation.CMVUpdater;



/**
 * sugcmvholders are associated with a unique value. 
 * if you need all the SugCMV for a metadataset
 * use SugCMVHolderStorage.getCMVCollectionFor
 * @author omotelet
 *
 */
public class SugCMVHolder extends CMVHolder {

	MetadataSetAttribute itsAttribute;
	MetadataSetValue itsValue;
	
	public SugCMVHolder(MetadataSetAttribute aAttribute, MetadataSetValue aValue) {
		itsAttribute= aAttribute;
		itsValue = aValue;
	}
	
	@Override
	public CMVUpdater getUpdater(MetadataSet aMetadataSet) {
		if (aMetadataSet == null) return null;
		if (!itsUpdaterMap.containsKey(aMetadataSet)){
			itsUpdaterMap.put(aMetadataSet, new SugCMVUpdater(aMetadataSet,itsAttribute,itsValue));
		}
			
		return itsUpdaterMap.get(aMetadataSet); 
	}

	@Override
	public String toString() {
		return "SugHolderFor"+itsAttribute+"("+itsValue+")";
	}
	
}
