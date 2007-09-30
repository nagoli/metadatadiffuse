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

import diffuse.metadata.MetadataSet;
import diffuse.metadata.MetadataSetAttribute;
import diffuse.propagation.CMVHolder;
import diffuse.propagation.CMVUpdater;



/**
 * resCMVHolders are associated with a attribute. 
 * 
 * @author omotelet
 *
 */
public abstract class ResCMVHolder extends CMVHolder {

	MetadataSetAttribute itsAttribute;
	
	
	public ResCMVHolder(MetadataSetAttribute aAttribute) {
		itsAttribute= aAttribute;
	}
	
	@Override
	public CMVUpdater getUpdater(MetadataSet aMetadataSet) {
		if (aMetadataSet == null) return null;
		if (!itsUpdaterMap.containsKey(aMetadataSet)){
			itsUpdaterMap.put(aMetadataSet, createUpdater(aMetadataSet));
		}	
		return itsUpdaterMap.get(aMetadataSet); 
	}

	protected abstract ResCMVUpdater createUpdater(MetadataSet aMetadataSet) ;

	@Override
	public String toString() {
		return "ResHolderFor"+itsAttribute;
	}
	
}
