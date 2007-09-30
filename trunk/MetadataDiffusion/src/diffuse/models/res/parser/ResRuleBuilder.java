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
package diffuse.models.res.parser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import diffuse.metadata.MetadataSetAttribute;
import diffuse.metadata.MetadataSetRelationType;
import diffuse.metadata.MetadataSetValue;
import diffuse.models.res.rules.DownResRule;
import diffuse.models.res.rules.ResRule;
import diffuse.models.res.rules.UpResRule;


/**
 * User: omotelet Date: Apr 12, 2005 Time: 6:41:34 PM.
 */
public abstract class ResRuleBuilder {

	protected String itsAttributeName;

	protected String itsRelationName;

	protected String itsFormule;

	protected int itsRestrictionOperator;
	protected int itsFusionOperator;
	protected  List<String> itsValues = new ArrayList<String>();
	
	static RestrictionParser itsParser = new RestrictionParser(
			new StringReader(""));

	/**
	 * @param aRelationName
	 * @param aFormule
	 * @param aAttributeName
	 */
	public ResRuleBuilder(String aAttributeName, String aRelationName,
			String aFormule) {
		itsAttributeName = aAttributeName;
		itsRelationName = aRelationName;
		itsFormule = aFormule;
		RestrictionParser.ReInit(new StringReader(itsFormule));
		try {
			RestrictionParser.Parse(this);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void setRestrictionOperator(int aResOperator) {
		itsRestrictionOperator = aResOperator;
	}

	public void setFusionOperator(int aFusionOperator) {
		itsFusionOperator = aFusionOperator;
	}
	
	
	public void addValue(String aValue){
		itsValues.add(aValue);
	}

	public ResRule getRule(){
		if (itsRestrictionOperator==ResRule.SUPEQ || itsRestrictionOperator == ResRule.CONTAINS)
			return new DownResRule(getAttribute(),getRelationType(),itsRestrictionOperator,itsFusionOperator,getValue());
		if (itsRestrictionOperator==ResRule.INFEQ || itsRestrictionOperator == ResRule.CONTAINED)
			return new UpResRule(getAttribute(),getRelationType(),itsRestrictionOperator,itsFusionOperator,getValue());
		
		return null;
	}
	
	public abstract MetadataSetAttribute getAttribute();
	
	public abstract MetadataSetRelationType getRelationType();
	
	public MetadataSetValue getValue() {
		return getAttribute().createValueWith(itsValues);
	}
	
	
	

}
