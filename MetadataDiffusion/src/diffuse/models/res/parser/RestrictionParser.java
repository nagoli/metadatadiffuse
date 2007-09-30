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


@SuppressWarnings("all")
public class RestrictionParser implements RestrictionParserConstants {

  static final public void Parse(ResRuleBuilder aBuilder) throws ParseException {
    RestrictionOperator(aBuilder);
    FusionOperator(aBuilder);
    jj_consume_token(0);
  }

  static final public void RestrictionOperator(ResRuleBuilder aBuilder) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INFEQ:
      jj_consume_token(INFEQ);
           aBuilder.setRestrictionOperator(diffuse.models.res.rules.ResRule.INFEQ);
      break;
    case SUPEQ:
      jj_consume_token(SUPEQ);
           aBuilder.setRestrictionOperator(diffuse.models.res.rules.ResRule.SUPEQ);
      break;
    case CONTAINS:
      jj_consume_token(CONTAINS);
               aBuilder.setRestrictionOperator(diffuse.models.res.rules.ResRule.CONTAINS);
      break;
    case CONTAINED:
      jj_consume_token(CONTAINED);
               aBuilder.setRestrictionOperator(diffuse.models.res.rules.ResRule.CONTAINED);
      break;
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void FusionOperator(ResRuleBuilder aBuilder) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MIN:
      jj_consume_token(MIN);
         aBuilder.setFusionOperator( diffuse.models.res.rules.ResRule.MIN);
      break;
    case MAX:
      jj_consume_token(MAX);
         aBuilder.setFusionOperator( diffuse.models.res.rules.ResRule.MAX);
      break;
    case UNION:
      jj_consume_token(UNION);
            aBuilder.setFusionOperator( diffuse.models.res.rules.ResRule.UNION);
      break;
    case INTERSECTION:
      jj_consume_token(INTERSECTION);
                  aBuilder.setFusionOperator( diffuse.models.res.rules.ResRule.INTERSECTION);
      break;
    case 16:
      jj_consume_token(16);
      String(aBuilder);
      jj_consume_token(16);
                          aBuilder.setFusionOperator(diffuse.models.res.rules.ResRule.VALUE);
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void String(ResRuleBuilder aBuilder) throws ParseException {
 //Token id;
 String theString;
    //id=<STRING>  (id=<STRING>)* ("," String(aBuilder))* { aBuilder.addValue(id.image); }
      theString = Sentence();
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 17:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_1;
      }
      jj_consume_token(17);
      String(aBuilder);
    }
        aBuilder.addValue(theString);
  }

  static final public String Sentence() throws ParseException {
        Token id;
        String theString=null;
    id = jj_consume_token(STRING);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case STRING:
      theString = Sentence();
      break;
    default:
      jj_la1[3] = jj_gen;
      ;
    }
                String theNewString = id.image;
                if(theString!=null) theNewString = theNewString + " " +theString;
                {if (true) return  theNewString;}
    throw new Error("Missing return statement in function");
  }

  static private boolean jj_initialized_once = false;
  static public RestrictionParserTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  static public Token token, jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[4];
  static private int[] jj_la1_0;
  static {
      jj_la1_0();
   }
   private static void jj_la1_0() {
      jj_la1_0 = new int[] {0x1e00,0x101e0,0x20000,0x2000,};
   }

  public RestrictionParser(java.io.InputStream stream) {
     this(stream, null);
  }
  public RestrictionParser(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new RestrictionParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  public RestrictionParser(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new RestrictionParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  public RestrictionParser(RestrictionParserTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  public void ReInit(RestrictionParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  static final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.Vector jj_expentries = new java.util.Vector();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  static public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[18];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 4; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 18; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.addElement(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.elementAt(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  static final public void enable_tracing() {
  }

  static final public void disable_tracing() {
  }

 // private static int count=0;

}
