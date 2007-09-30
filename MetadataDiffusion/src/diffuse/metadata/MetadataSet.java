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
package diffuse.metadata;

import java.util.Map;
import java.util.Set;

/**
 * data structure for holding metadataset like e.g. LOM
 * @author omotelet
 *
 */


public interface MetadataSet {

	/**
	 * return a map of (relationType, related MetadataSets) for this metadataSet
	 * @return
	 */
	Map<? extends MetadataSetRelationType, ? extends Set<? extends MetadataSet>> getRelationTypeMap();

	
	/**
	 * return the set of related MetadataSets
	 * @return
	 */
	Set<? extends MetadataSet> getRelatedMetadataSet();

}
