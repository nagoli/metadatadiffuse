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

import diffuse.models.res.rules.DownResRule;
import diffuse.models.res.rules.ResRule;


public class DownResCMVUpdater extends ResCMVUpdater{

	public DownResCMVUpdater(MetadataSet aMetadataSet,
			MetadataSetAttribute aAttribute) {
		super(aMetadataSet, aAttribute);
	}

	//TODO create null if undifined or null
	@Override
	protected ResCMV createCMV(MetadataSetValue aValueIn) {
		return new DownResCMV(aValueIn, 0); //nature is defined by the rule!
	}
	
	
	@Override
	public CMV applyRule(MetadataSetRelationType aRelationType,
			List<CMV> aCMVList) {
		DownResRule theDownRule = ResRule.getDownRuleFor(itsAttribute, aRelationType);
		if (theDownRule!=null) return theDownRule.apply(aCMVList);
		else return null;
	}
	
	
	
	
}
