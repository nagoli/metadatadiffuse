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
package diffuse.models.res.rules;

import java.util.List;

import diffuse.metadata.MetadataSetAttribute;
import diffuse.metadata.MetadataSetRelationType;
import diffuse.metadata.MetadataSetValue;
import diffuse.models.res.ResCMV;
import diffuse.models.res.UpResCMV;
import diffuse.propagation.CMV;
import diffuse.propagation.CMVUtils;


public class UpResRule extends ResRule {
	
	
	public UpResRule(MetadataSetAttribute aAttribute,
			MetadataSetRelationType aRelationType, int aRestrictionOperator, int aFusionOperator,
			MetadataSetValue aValue) {
		super(aAttribute, aRelationType, aRestrictionOperator, aFusionOperator, aValue);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CMV apply(List<CMV> aCMVList) {
		if (itsFusionOperator == ResRule.VALUE)
			return new UpResCMV(itsValue,itsRestrictionOperator);
		if (itsFusionOperator == ResRule.MAX) //inverted 
			return ((ResCMV)CMVUtils.minCMV(aCMVList)).setNature(itsRestrictionOperator);
		if (itsFusionOperator == ResRule.MIN) //inverted
			return ((ResCMV)CMVUtils.maxCMV(aCMVList)).setNature(itsRestrictionOperator);
		if (itsFusionOperator == ResRule.UNION)
			return ((ResCMV)CMVUtils.unionCMV(aCMVList)).setNature(itsRestrictionOperator);
		if (itsFusionOperator == ResRule.INTERSECTION)
			return ((ResCMV)CMVUtils.intersectionCMV(aCMVList)).setNature(itsRestrictionOperator);
		else return null;
	}

}
