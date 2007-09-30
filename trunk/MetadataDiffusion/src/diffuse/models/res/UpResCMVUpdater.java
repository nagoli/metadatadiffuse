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
import diffuse.models.res.rules.ResRule;
import diffuse.models.res.rules.UpResRule;
import diffuse.propagation.CMV;

public class UpResCMVUpdater extends ResCMVUpdater{

	public UpResCMVUpdater(MetadataSet aMetadataSet,
			MetadataSetAttribute aAttribute) {
		super(aMetadataSet, aAttribute);
	}

	
	@Override
	protected ResCMV createCMV(MetadataSetValue aValueIn) {
		return new UpResCMV(aValueIn,0);//nature is defined by the rule
	}
	
	
	@Override
	public CMV applyRule(MetadataSetRelationType aRelationType,
			List<CMV> aCMVList) {
		UpResRule theUpRule = ResRule.getUpRuleFor(itsAttribute, aRelationType);
		if (theUpRule !=null)return theUpRule.apply(aCMVList);
		return null;
	}
	
	
	
	
}
