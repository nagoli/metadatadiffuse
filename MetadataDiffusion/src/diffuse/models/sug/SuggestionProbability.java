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

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

import diffuse.metadata.MetadataSetAttribute;
import diffuse.metadata.MetadataSetRelationType;

import diffuse.util.Couple;

/**
 * this class is responsible for holding the probabilities that a suggestion
 * results this probability is build with the xml resource
 * resources/SuggestionProbability.xml
 * 
 * The main of this class is responsible for building this file
 * 
 * @author omotelet
 */
public abstract class SuggestionProbability {

	
	
	public static final MathContext ITSPrecision = MathContext.DECIMAL64;
	public static final BigDecimal epsilon = new BigDecimal(0.00001,ITSPrecision);
	
	
	/**
	 * 
	 */
	protected static SuggestionProbability ITSInstance ;

	/**
	 * 
	 * 
	 * @return 
	 */
	public static SuggestionProbability getInstance() throws Exception{
		if (ITSInstance == null ) 
			throw new Exception("Uninitializes Suggestion Probability class");
		return ITSInstance;
	}

	/**
	 * 
	 */
	Map<Couple<MetadataSetAttribute, MetadataSetRelationType>, BigDecimal> itsProbas = new HashMap<Couple<MetadataSetAttribute, MetadataSetRelationType>, BigDecimal>();

	/**
	 * 
	 */
	public SuggestionProbability() {
	}
	
	/**
	 * 
	 * 
	 * @param aURL 
	 */
	
	/**
	 * return the proba for aAttribute and aRelationType.
	 * 
	 * @param aRelationType 
	 * @param aAttribute 
	 * 
	 * @return 
	 */
	public BigDecimal getProbaFor(MetadataSetAttribute aAttribute,
			MetadataSetRelationType aRelationType) {
		//aRelationType=aRelationType.getContrary();
		BigDecimal theProba ; 
		Couple<MetadataSetAttribute, MetadataSetRelationType> theCouple = new Couple<MetadataSetAttribute, MetadataSetRelationType>(
				aAttribute, aRelationType);
		if (itsProbas.containsKey(theCouple)){
			 theProba = itsProbas.get(theCouple);
			 if (theProba.doubleValue()>1-epsilon.doubleValue())
				 theProba= BigDecimal.ONE.subtract(epsilon,ITSPrecision);
			 if (theProba.doubleValue()<epsilon.doubleValue())
				 theProba = epsilon;
		}
		else theProba =epsilon;
		return theProba;
		
		//return new BigDecimal(0.7);
	}

	/**
	 * set the proba for aAttribute and aRelationType.
	 * 
	 * @param aRelationType 
	 * @param aAttribute 
	 * @param aProba 
	 * 
	 * @return 
	 */
	public double setProbaFor(MetadataSetAttribute aAttribute,
			MetadataSetRelationType aRelationType, BigDecimal aProba) {
		BigDecimal theExProba = itsProbas.put(
				new Couple<MetadataSetAttribute, MetadataSetRelationType>(aAttribute,
						aRelationType), aProba);
		if (theExProba != null)
			return theExProba.doubleValue();
		return 0;
	}

	/**
	 * 
	 * 
	 * @return 
	 */
	//public abstract Element makeXMLElement() ;
	

}
