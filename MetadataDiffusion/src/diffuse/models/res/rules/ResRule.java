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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import diffuse.metadata.MetadataSetAttribute;
import diffuse.metadata.MetadataSetRelationType;
import diffuse.metadata.MetadataSetValue;
import diffuse.propagation.CMV;


public abstract class ResRule {

	public final static int INFEQ = 0;
	public final static int SUPEQ = 1;
	public final static int CONTAINS = 2;
	public final static int CONTAINED = 3;
	
	public final static int MAX = 0;
	public final static int MIN = 1;
	public final static int UNION = 2;
	public final static int INTERSECTION = 3;
	public final static int VALUE = 4;
	
	
	static Map<MetadataSetAttribute, Map<MetadataSetRelationType,DownResRule>> ITSDownRuleMap = new HashMap<MetadataSetAttribute, Map<MetadataSetRelationType,DownResRule>>();
	static Map<MetadataSetAttribute, Map<MetadataSetRelationType, UpResRule>> ITSUpRuleMap = new HashMap<MetadataSetAttribute, Map<MetadataSetRelationType,UpResRule>>();
	
	
	
	public static void register(MetadataSetAttribute aAttribute, MetadataSetRelationType aRelationType,ResRule aResRule){
		if (aResRule instanceof DownResRule){
			Map<MetadataSetRelationType,DownResRule> theMap = ITSDownRuleMap.get(aAttribute);
			if (theMap==null){
				theMap = new HashMap<MetadataSetRelationType, DownResRule>();
				ITSDownRuleMap.put(aAttribute, theMap);
			}
			theMap.put(aRelationType, (DownResRule)aResRule);
		}
		if (aResRule instanceof UpResRule){
			Map<MetadataSetRelationType,UpResRule> theMap = ITSUpRuleMap.get(aAttribute);
			if (theMap==null){
				theMap = new HashMap<MetadataSetRelationType, UpResRule>();
				ITSUpRuleMap.put(aAttribute, theMap);
			}
			theMap.put(aRelationType, (UpResRule)aResRule);
		}
		
	}
	
	
	public static DownResRule getDownRuleFor(MetadataSetAttribute aAttribute, MetadataSetRelationType aRelationType){
		Map<MetadataSetRelationType,DownResRule> theMap =  ITSDownRuleMap.get(aAttribute);
		if (theMap==null) return null;
		else return theMap.get(aRelationType);
	}
	
	public static UpResRule getUpRuleFor(MetadataSetAttribute aAttribute, MetadataSetRelationType aRelationType){
		Map<MetadataSetRelationType,UpResRule> theMap =  ITSUpRuleMap.get(aAttribute);
		if (theMap==null) return null;
		else return theMap.get(aRelationType);
	}
	
	public static boolean hasDownResRule(MetadataSetAttribute aAttribute){
		return (ITSDownRuleMap.get(aAttribute)!=null);
	}
	
	public static boolean hasUpResRule(MetadataSetAttribute aAttribute){
		return (ITSUpRuleMap.get(aAttribute)!=null);
	}
	
	MetadataSetAttribute itsAttribute;
	MetadataSetRelationType itsRelationType;
	int itsRestrictionOperator;
	int itsFusionOperator;
	MetadataSetValue itsValue;
	
	public ResRule(MetadataSetAttribute aAttribute, MetadataSetRelationType aRelationType, int aRestrictionOperator, int aFusionOperator, MetadataSetValue aValue) {
		itsAttribute = aAttribute;
		itsRelationType = aRelationType;
		itsRestrictionOperator = aRestrictionOperator;
		itsFusionOperator = aFusionOperator;
		itsValue = aValue;
	}
	
	
	public abstract CMV apply(List<CMV> aCMVList);
	
	
	
	
}
