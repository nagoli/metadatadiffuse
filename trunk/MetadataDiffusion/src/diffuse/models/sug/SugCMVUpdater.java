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

import java.util.List;

import diffuse.metadata.MetadataSet;
import diffuse.metadata.MetadataSetAttribute;
import diffuse.metadata.MetadataSetRelationType;
import diffuse.metadata.MetadataSetValue;
import diffuse.propagation.CMV;
import diffuse.propagation.CMVUpdater;
import diffuse.propagation.CMVUtils;


public class SugCMVUpdater extends CMVUpdater {

	MetadataSetAttribute itsAttribute;
	MetadataSetValue itsValue;

	public SugCMVUpdater(MetadataSet aMetadataSet,
			MetadataSetAttribute aAttribute, MetadataSetValue aValue) {
		super(aMetadataSet);
		itsAttribute = aAttribute;
		itsValue = aValue;
		init();
	}

	@Override
	public CMV applyRule(MetadataSetRelationType aRelationType,
			List<CMV> aCMVList) {
		CMV theCMV = CMVUtils.maxCMV(aCMVList);
		if (theCMV == null || !(theCMV instanceof SugCMV))
			return null;
		SugCMV theNewCMV = (SugCMV) theCMV.clone();
		try {
			theNewCMV.addFactorToWeight(SuggestionProbability.getInstance()
					.getProbaFor(itsAttribute, aRelationType).doubleValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theNewCMV;
	}

	@Override
	public CMV getCMVFromOriginalValue() {
		MetadataSetValue theValueIn = itsAttribute.getValueIn(itsMetadataSet);
		if (theValueIn != null && theValueIn.contains(itsValue))
			return new SugCMV(itsValue, 1);
		return new SugCMV(itsValue, 0);
	}

	/***
	 * return the max of the two CMV
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
