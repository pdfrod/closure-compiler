// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: conformance.proto

package com.google.javascript.jscomp;

public interface ConformanceConfigOrBuilder
    extends com.google.protobuf.MessageOrBuilder {

  // repeated .jscomp.Requirement requirement = 1;
  /**
   * <code>repeated .jscomp.Requirement requirement = 1;</code>
   */
  java.util.List<com.google.javascript.jscomp.Requirement> 
      getRequirementList();
  /**
   * <code>repeated .jscomp.Requirement requirement = 1;</code>
   */
  com.google.javascript.jscomp.Requirement getRequirement(int index);
  /**
   * <code>repeated .jscomp.Requirement requirement = 1;</code>
   */
  int getRequirementCount();
  /**
   * <code>repeated .jscomp.Requirement requirement = 1;</code>
   */
  java.util.List<? extends com.google.javascript.jscomp.RequirementOrBuilder> 
      getRequirementOrBuilderList();
  /**
   * <code>repeated .jscomp.Requirement requirement = 1;</code>
   */
  com.google.javascript.jscomp.RequirementOrBuilder getRequirementOrBuilder(
      int index);
}