/*
 * Copyright 2013 The Closure Compiler Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.javascript.jscomp.fuzzing;


import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * @author zplin@google.com (Zhongpeng Lin)
 */
public class SymbolTable {
  private Stack<List<String>> storage = new Stack<List<String>>();
  private Random random;
  private int size;

  public SymbolTable(Random random) {
    this.random = random;
    ArrayList<String> globalScope = Lists.newArrayList(
        "Array",
        "Boolean",
        "Function",
        "Number",
        "Object",
        "RegExp",
        "String",
        "Error",
        "JSON",
        "Math",
        "NaN",
        "undefined");
    size = globalScope.size();
    storage.push(globalScope);
  }

  public void addScope() {
    ArrayList<String> newScope = Lists.newArrayList("arguments");
    storage.push(newScope);
  }

  public void removeScope() {
    size -= storage.peek().size();
    storage.pop();
  }

  public void addSymbol(String symbol) {
    storage.peek().add(symbol);
    size++;
  }

  public int getSize() {
    return size;
  }

  public int getNumScopes() {
    return storage.size();
  }

  public boolean hasNonLocals() {
    return size - storage.peek().size() > 0;
  }

  public String getRandomSymbol(boolean excludeLocal) {
    if (excludeLocal) {
      Preconditions.checkArgument(getNumScopes() > 1);
    } else {
      Preconditions.checkArgument(getNumScopes() > 0);
    }
    List<String> scope = getRandomScope(excludeLocal);
    String sym = scope.get(random.nextInt(scope.size()));
    if (excludeLocal && storage.peek().indexOf(sym) != -1) {
      // the symbol has been shadowed
      return null;
    } else {
      return sym;
    }
  }

  /**
   * Get a scope randomly. The more variables/function the scope has, the more
   * likely it will be chosen
   */
  private List<String> getRandomScope(boolean excludeLocal) {
    ArrayList<List<String>> scopes = new ArrayList<List<String>>(storage);
    ArrayList<Double> weights = Lists.newArrayListWithCapacity(getNumScopes());
    int i;
    for (i = 0; i < storage.size() - 1; i++) {
      List<String> s = storage.get(i);
      weights.add(Double.valueOf(s.size()));
    }
    if (!excludeLocal) {
      List<String> s = storage.get(i);
      weights.add(Double.valueOf(s.size()));
    }
    DiscreteDistribution<List<String>> distribution =
        new DiscreteDistribution<List<String>>(random, scopes, weights);
    return distribution.nextItem();
  }
}
