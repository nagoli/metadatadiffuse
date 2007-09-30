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

import java.util.HashMap;

import diffuse.metadata.MetadataSet;



/**
 * abstract class for Diffusion Value Holder
 * a difusion value holder has a method getValue.
 * 
 * @author omotelet
 */

public abstract class CMVHolder {

	protected HashMap<MetadataSet, CMVUpdater> itsUpdaterMap = new HashMap<MetadataSet, CMVUpdater>();
	
	
	/**
	 * return the value associated with aMetadataSet and aAttribute
	 * return a new value for aMetadataSet and aAttribute if not exisiting.
	 * 
	 * @param aMetadataSet 
	 * 
	 * @return 
	 */
	public abstract CMVUpdater getUpdater(MetadataSet aMetadataSet);

	
	/**
	 * 
	 */
	public void reset(){
		itsUpdaterMap.clear();
	}


	/**
	 * 
	 * 
	 * @param aListOfChangedMetadataSets 
	 */
	//public abstract void initUpdate(Collection<MetadataSet> aListOfChangedMetadataSets) ;
	
	/**
	 * 
	 */
	//public abstract void endUpdate();
	
	/**
	 * 
	 * 
	 * @return 
	 */
	public int size(){
		return itsUpdaterMap.size();
	}
}
