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

import java.util.Collection;
import java.util.Collections;

public class CMVUtils {

	/**
	 * return true if the first one is max or equivalent to the second one
	 * 
	 * @param aFirstCMV
	 * @param aSecondCMV
	 * @return
	 */
	public static boolean isFirstCMVMax(CMV aFirstCMV, CMV aSecondCMV) {
		return aFirstCMV.compareTo(aSecondCMV) >= 0; 
	}

	@SuppressWarnings("unchecked")
	public static CMV maxCMV(Collection<? extends CMV> aCMVs) {
		if (aCMVs == null)
			return null;
		return Collections.max(aCMVs);
	}

	@SuppressWarnings("unchecked")
	public static CMV minCMV(Collection<? extends CMV> aCMVs) {
		if (aCMVs == null)
			return null;
		return Collections.min(aCMVs);
	}
	
	public static CMV unionCMV(Collection<? extends CMV> aCMVs) {
		if (aCMVs == null)
			return null;
		CMV theUnion=null;
		for (CMV theCmv : aCMVs) {
			if (theUnion==null) theUnion=theCmv;
			else theUnion = theUnion.union(theCmv);
		}
		return theUnion;
	}
	
	
	public static CMV intersectionCMV(Collection<? extends CMV> aCMVs) {
		if (aCMVs == null)
			return null;
		CMV theInter=null;
		for (CMV theCmv : aCMVs) {
			if (theInter==null) theInter=theCmv;
			else theInter = theInter.intersection(theCmv);
		}
		return theInter;
	}
	
	
	
	
}
