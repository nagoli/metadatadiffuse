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

import java.util.List;

import diffuse.metadata.MetadataSet;
import diffuse.metadata.MetadataSetAttribute;
import diffuse.metadata.MetadataSetRelationType;
import diffuse.metadata.MetadataSetValue;
import diffuse.propagation.CMV;
import diffuse.propagation.CMVUpdater;
import diffuse.propagation.CMVUtils;


public abstract class ResCMVUpdater extends CMVUpdater {

	MetadataSetAttribute itsAttribute;

	public ResCMVUpdater(MetadataSet aMetadataSet,
			MetadataSetAttribute aAttribute) {
		super(aMetadataSet);
		itsAttribute = aAttribute;
		init();
	}

	@Override
	public abstract CMV applyRule(MetadataSetRelationType aRelationType,
			List<CMV> aCMVList) ;

	@Override
	public CMV getCMVFromOriginalValue() {
		MetadataSetValue theValueIn = itsAttribute.getValueIn(itsMetadataSet);
		return createCMV(theValueIn);
	}

	/**
	 * the nature of the CMV at that point is not compulsory. 
	 * When used out of the CMV process, the CMV are the result of a rule (ResCMVUpdater.getDifusionWithoutOriginal)
	 * Thus the rules are responsible for aggregating the nature. 
	 * @param aValueIn
	 * @return
	 */
	protected abstract ResCMV createCMV(MetadataSetValue aValueIn);
	
	/**
	 * fusion = max (max is defined properly by the compare method of the ResCMV)
	 */
	@Override
	public CMV fusion(CMV aFirstCMV, CMV aSecondCMV) {
		if (aFirstCMV == null)
			return aSecondCMV;
		if (aSecondCMV == null)
			return aFirstCMV;
		if (CMVUtils.isFirstCMVMax(aFirstCMV, aSecondCMV))
			return aFirstCMV;
		else
			return aSecondCMV;
	}

	@Override
	public String toString() {
		return "Updater for " + itsAttribute + " in " + itsMetadataSet + ": "
				+ itsCMV;
	}

}
