/* LanguageTool, a natural language style checker 
 * Copyright (C) 2005 Daniel Naber (http://www.danielnaber.de)
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */

package org.languagetool;

import junit.framework.TestCase;
import org.languagetool.chunking.ChunkTag;

import java.util.Collections;

public class AnalyzedTokenTest extends TestCase {

  public void testToString() {
    final AnalyzedToken testToken = new AnalyzedToken("word", "POS", "lemma");
    assertEquals("lemma/POS", testToken.toString());
    assertEquals("lemma", testToken.getLemma());
    final AnalyzedToken testToken2 = new AnalyzedToken("word", "POS", null);
    assertEquals("word/POS", testToken2.toString());
    assertEquals(null, testToken2.getLemma());
    assertEquals("word", testToken2.getToken());
  }
  
  public void testMatches() {
    final AnalyzedToken testToken1 = new AnalyzedToken("word", "POS", "lemma");
    assertFalse(testToken1.matches(new AnalyzedToken("", null, null)));
    assertTrue(testToken1.matches(new AnalyzedToken("word", null, null)));
    assertTrue(testToken1.matches(new AnalyzedToken("word", "POS", null)));
    assertTrue(testToken1.matches(new AnalyzedToken("word", "POS", "lemma")));
    assertFalse(testToken1.matches(new AnalyzedToken("word", "POS1", "lemma")));
    assertFalse(testToken1.matches(new AnalyzedToken("word1", "POS", "lemma")));
    assertFalse(testToken1.matches(new AnalyzedToken("word", "POS", "lemma1")));
    assertTrue(testToken1.matches(new AnalyzedToken("", "POS", "lemma")));
    assertTrue(testToken1.matches(new AnalyzedToken("", null, "lemma")));

    assertTrue(makeToken("word", "POS", "lemma", null).matches(makeToken("word", "POS", "lemma", null)));
    assertTrue(makeToken("word", "POS", "lemma", "chunk").matches(makeToken("word", "POS", "lemma", "chunk")));
    assertTrue(makeToken("word", "POS", "lemma", "chunk").matches(makeToken("word", "POS", "lemma", null)));

    assertFalse(makeToken("word", "POS", "lemma", "chunk1").matches(makeToken("word", "POS", "lemma", "chunk2")));
    assertFalse(makeToken("word", "POS", "lemma", null).matches(makeToken("word", "POS", "lemma", "chunk")));
    assertFalse(testToken1.matches(makeToken("word", "POS", "lemma", "chunk2")));
  }

  private AnalyzedToken makeToken(String token, String pos, String lemma, String chunk) {
    AnalyzedToken analyzedToken = new AnalyzedToken(token, pos, lemma);
    if (chunk != null) {
      analyzedToken.setChunkTags(Collections.singletonList(new ChunkTag(chunk)));
    }
    return analyzedToken;
  }
  
}
