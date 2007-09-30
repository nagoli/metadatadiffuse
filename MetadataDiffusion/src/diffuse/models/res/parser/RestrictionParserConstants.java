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

public interface RestrictionParserConstants {

  int EOF = 0;
  int MAX = 5;
  int MIN = 6;
  int UNION = 7;
  int INTERSECTION = 8;
  int INFEQ = 9;
  int SUPEQ = 10;
  int CONTAINS = 11;
  int CONTAINED = 12;
  int STRING = 13;
  int ATT_ID = 14;
  int NUM = 15;

  int DEFAULT = 0;

  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "\"MAX\"",
    "\"MIN\"",
    "\"UNION\"",
    "\"INTERSECTION\"",
    "\"INFEQ\"",
    "\"SUPEQ\"",
    "\"CONTAINS\"",
    "\"CONTAINED\"",
    "<STRING>",
    "<ATT_ID>",
    "<NUM>",
    "\"\\\'\"",
    "\",\"",
  };

}
