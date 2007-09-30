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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import diffuse.metadata.MetadataSet;
import diffuse.util.fibheapMax.FibHeapWithIncreasingInsert;


/**
 * this class is responsible for the update of the CMV it is based on a
 * FIbonacci Heap to ensure performance to nlog(n)
 *  - value change = make CMV update of modified MetadataSet and diffuse - edge
 * removed,added, or modified = make CMV update of two related MetadataSet and
 * diffuse
 * 
 * @author omotelet
 */

public class CMVDiffusion {

	/**
	 * diffuse the changes of aMetadataSet for aAttribute for the
	 * DifValueHolders given as arguments.
	 * 
	 * @param aMetadataSet
	 * @param aHolderList
	 * 
	 * @return
	 */
	public static Set<MetadataSet> diffuseChangesOf(MetadataSet aMetadataSet,
			Collection<? extends CMVHolder> aHolderList) {
		Set<MetadataSet> theModifiedMetadataSets = new HashSet<MetadataSet>();
		for (CMVHolder theHolder : aHolderList)
			theModifiedMetadataSets.addAll(diffuseChangesOf(aMetadataSet,
					theHolder));
		return theModifiedMetadataSets;
	}

	/**
	 * diffuse the changes of aMetadataSet for aAttribute for the DifValueHolder
	 * given as argument.
	 * 
	 * @param aHolder
	 * @param aMetadataSet
	 * 
	 * @return
	 */
	public static Set<MetadataSet> diffuseChangesOf(MetadataSet aMetadataSet,
			CMVHolder aHolder) {
		List<MetadataSet> theMetadataSetsToModify = new ArrayList<MetadataSet>();
		theMetadataSetsToModify.add(aMetadataSet);
		Set<MetadataSet> theModifiedMetadataSets = makeDiffusion(
				theMetadataSetsToModify, aHolder,false);
		return theModifiedMetadataSets;
	}

	/**
	 * diffuse the changes of the MetadataSets of aMetadataSetList for
	 * aAttribute for the DifValueHolders given as arguments.
	 * if isGlobalUpdate (i.e., all the metadataset are included in the update) optimized diffusion avoiding neutral value diffusion
	 * @param aHolderList
	 * @param aMetadataSetList
	 * 
	 * @return
	 */
	public static Set<MetadataSet> diffuseChangesOf(
			Collection<? extends MetadataSet> aMetadataSetList,
			Collection<? extends CMVHolder> aHolderList, boolean isGlobalUpdate) {
		Set<MetadataSet> theModifiedMetadataSets = new HashSet<MetadataSet>();
		for (CMVHolder theHolder : aHolderList)
			theModifiedMetadataSets.addAll(diffuseChangesOf(aMetadataSetList,
					theHolder,isGlobalUpdate));
		return theModifiedMetadataSets;

	}

	/**
	 * diffuse the changes of the MetadataSets of aMetadataSetList for
	 * aAttribute for the DifValueHolder given as argument.
	 * if isGlobalUpdate (i.e., all the metadataset are included in the update) optimized diffusion avoiding neutral value diffusion
	 * @param aHolder
	 * @param aMetadataSetList
	 * 
	 * @return
	 */
	public static Set<MetadataSet> diffuseChangesOf(
			Collection<? extends MetadataSet> aMetadataSetList,
			CMVHolder aHolder, boolean isGlobalUpdate) {
		Set<MetadataSet> theModifiedMetadataSets = makeDiffusion(
				aMetadataSetList, aHolder,isGlobalUpdate);
		// ITSComplexityLog.println();
		// System.out.println(aHolder.toString()+": " +count);
		return theModifiedMetadataSets;
	}

	//static int count =0;
	//static long totaltime =0;
	
	/**
	 * 
	 * 
     * @param aHolder
	 * @param aListOfChangedMetadataSets
	 * 
	 * @return
	 */
	static Set<MetadataSet> makeDiffusion(
			Collection<? extends MetadataSet> aListOfChangedMetadataSets,
			CMVHolder aHolder, boolean isGlobalUpdate) {
		// long timestamp = System.currentTimeMillis();
		
		// update the changed MetadataSets CMVs
		FibHeapWithIncreasingInsert<CMVUpdater> theFibHeap = new FibHeapWithIncreasingInsert<CMVUpdater>();
		Set<MetadataSet> theModifiedElements = new HashSet<MetadataSet>();
		for (MetadataSet theMetadataSet : aListOfChangedMetadataSets) {
			if (theMetadataSet == null)
				continue;
			CMVUpdater theCMVUpdater = aHolder.getUpdater(theMetadataSet);
			theCMVUpdater.updateCMV();
			if (!(theCMVUpdater.getDiffusion().isNeutral()&&isGlobalUpdate))
				theFibHeap.insert(theCMVUpdater);
			theModifiedElements.add(theMetadataSet);
		}
		//int countLocal = 0;
		while (!theFibHeap.isEmpty()) {
		//	countLocal++;
			CMVUpdater theUpdater = theFibHeap.removeMax().getData();
			List<CMVUpdater> theDiffusionTurn = diffusionTurn(theUpdater,
					aHolder);
			for (CMVUpdater theModifiedCMVUpdaters : theDiffusionTurn) {
				theFibHeap.insert(theModifiedCMVUpdaters);
				theModifiedElements.add(theModifiedCMVUpdaters.itsMetadataSet);
			}
		}
		/*count +=countLocal; 
		totaltime += System.currentTimeMillis() - timestamp;
		System.out.println("1-CMVLocalCount="+countLocal);
		System.out.println("2-CMVDifTime="+totaltime);
		System.out.println("3-CMVDifCount="+count);*/
		return theModifiedElements;
	}

	/**
	 * Make a diffusion turn, i.e., diffuse the changes of aChangedMetadataSet
	 * to its neighbors if this neighbor have been changed it is return as part
	 * of a list of updated MetadataSet
	 * 
	 * @param aHolder
	 * @param aChangedCMVUpdater
	 * 
	 * @return
	 */
	static List<CMVUpdater> diffusionTurn(CMVUpdater aChangedCMVUpdater,
			CMVHolder aHolder) {
		CMV theChangedValue = aChangedCMVUpdater.getDiffusion();
		List<CMVUpdater> theModifiedCMVUpdaters = new ArrayList<CMVUpdater>();
		Set<? extends MetadataSet> theMetadataSets = aChangedCMVUpdater.itsMetadataSet
				.getRelatedMetadataSet();
		if (theMetadataSets != null) {
			for (MetadataSet theRelatedMetadataSet : theMetadataSets) {
				if (theRelatedMetadataSet == null)
					continue;
				CMVUpdater theRelatedValue = aHolder
						.getUpdater(theRelatedMetadataSet);
				boolean hasChanged = theRelatedValue.updateCMV(
						aChangedCMVUpdater.itsMetadataSet, theChangedValue);
				if (hasChanged)
					theModifiedCMVUpdaters.add(theRelatedValue);
			}
		}
		return theModifiedCMVUpdaters;
	}

	/**
	 * 
	 */
	static Date ITSDate = new Date(System.currentTimeMillis());

	/**
	 * 
	 */
/*	@SuppressWarnings("deprecation")
	static String ITSDateString = "" + ITSDate.getMonth() + "_"
			+ ITSDate.getDate() + "_" + ITSDate.getHours() + "_"
			+ ITSDate.getMinutes() + "_";
*/
/**
	 * 
	 */
/*	static PrintStream ITSComplexityLog;
	static {
		try {
			ITSComplexityLog = new PrintStream(
					"/USERDISK/these/LMProjects/tests/logs/" + ITSDateString
							+ "DiffusionComplexity.log");
		} catch (Exception e) {
			System.out.println("--------Diffusion Complexity file not created");
		}
	}
*/
}
