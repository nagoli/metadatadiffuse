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
package diffuse.util;

import java.io.Serializable;



public class Couple<A,B> implements Serializable, Comparable<Couple<A,B>>{

	A itsLeftElement;
	B itsRightElement;
	
	public Couple(A aLeftElement,B aRightElement) {
		itsLeftElement = aLeftElement;
		itsRightElement=aRightElement;
	}


	public Couple(Couple<A,B> aCouple) {
		itsLeftElement = aCouple.itsLeftElement;
		itsRightElement=aCouple.itsRightElement;
	}

	
	public A getLeftElement() {
		return itsLeftElement;
	}

	public void setLeftElement(A aLeftElement) {
		itsLeftElement = aLeftElement;
	}

	public B getRightElement() {
		return itsRightElement;
	}

	public void setRightElement(B aRightElement) {
		itsRightElement = aRightElement;
	}
	
	@Override
	public int hashCode() {
		return ((itsLeftElement!=null)? itsLeftElement.hashCode(): 0) + ((itsRightElement!=null)? itsRightElement.hashCode():0);
	}
	
	@Override
	public String toString() {	
		return "("+getLeftElement()+","+getRightElement()+")";
	}
	
	@SuppressWarnings("unchecked")
	public int compareTo(Couple<A,B> aCouple) {
		if (itsLeftElement instanceof Comparable)
		return ((Comparable)itsLeftElement).compareTo(aCouple.itsLeftElement);
		return -1;
	}
	@Override
	public boolean equals(Object aObj) {
		if (aObj instanceof Couple) {
			Couple theOtherCouple = (Couple) aObj;
			return theOtherCouple.getLeftElement().equals(itsLeftElement)
			&& theOtherCouple.getRightElement().equals(itsRightElement);
		}
		return false;
	}
}
