///*
// * Copyright (c) Meta Platforms, Inc. and affiliates.
// *
// * This source code is licensed under the MIT license found in the
// * LICENSE file in the root directory of this source tree.
// */

// THIS FILE WAS CONVERTED TO KOTLIN BY COPYING AND PASTING
// react-native/packages/react-native/ReactAndroid/src/test/java/com/facebook/react/uimanager/UIManagerModuleTest.java
// INTO THIS FILE

//package com.facebook.react.uimanager
//
//import org.assertj.core.api.Assertions.assertThat
//import org.mockito.ArgumentMatchers.any
//import org.mockito.Mockito.doAnswer
//import org.mockito.Mockito.mock
//import org.mockito.Mockito.`when`
//import android.graphics.Color
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import com.facebook.react.ReactRootView
//import com.facebook.react.bridge.Arguments
//import com.facebook.react.bridge.CatalystInstance
//import com.facebook.react.bridge.JavaOnlyArray
//import com.facebook.react.bridge.JavaOnlyMap
//import com.facebook.react.bridge.ReactApplicationContext
//import com.facebook.react.bridge.ReactTestHelper
//import com.facebook.react.modules.core.ChoreographerCompat
//import com.facebook.react.modules.core.ReactChoreographer
//import com.facebook.react.views.text.ReactRawTextManager
//import com.facebook.react.views.text.ReactRawTextShadowNode
//import com.facebook.react.views.text.ReactTextViewManager
//import com.facebook.react.views.view.ReactViewGroup
//import com.facebook.react.views.view.ReactViewManager
//import org.junit.Before
//import org.junit.Ignore
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.mockito.invocation.InvocationOnMock
//import org.mockito.stubbing.Answer
//import org.powermock.api.mockito.PowerMockito
//import org.powermock.core.classloader.annotations.PowerMockIgnore
//import org.powermock.core.classloader.annotations.PrepareForTest
//import org.powermock.modules.junit4.rule.PowerMockRule
//import org.robolectric.RobolectricTestRunner
//import org.robolectric.RuntimeEnvironment
//import com.facebook.testutils.shadows.ShadowSoLoader
//
///** Tests for [UIManagerModule].  */
//@PrepareForTest([Arguments::class, ReactChoreographer::class])
//@RunWith(RobolectricTestRunner::class)
//@PowerMockIgnore(["org.mockito.*", "org.robolectric.*", "androidx.*", "android.*"])
//@Config(shadows =, ShadowSoLoader =, ,)
//class UIManagerModuleTest {
//  @Rule
//  var rule: PowerMockRule = PowerMockRule()
//  private var mReactContext: ReactApplicationContext? = null
//  private var mCatalystInstanceMock: CatalystInstance? = null
//  private var mPendingFrameCallbacks: java.util.ArrayList<ChoreographerCompat.FrameCallback>? = null
//  @Before
//  fun setUp() {
//    PowerMockito.mockStatic(Arguments::class.java, ReactChoreographer::class.java)
//    val choreographerMock: ReactChoreographer = mock(ReactChoreographer::class.java)
//    PowerMockito.`when`(Arguments.createArray())
//      .thenAnswer(
//        object : Answer<Any?>() {
//          @kotlin.Throws(Throwable::class)
//          fun answer(invocation: InvocationOnMock?): Any {
//            return JavaOnlyArray()
//          }
//        })
//    PowerMockito.`when`(Arguments.createMap())
//      .thenAnswer(
//        object : Answer<Any?>() {
//          @kotlin.Throws(Throwable::class)
//          fun answer(invocation: InvocationOnMock?): Any {
//            return JavaOnlyMap()
//          }
//        })
//    // TODO: CA
//    PowerMockito.`when`(ReactChoreographer.getInstance()).thenAnswer(object : Answer<Any?>() {
//      @kotlin.Throws(Throwable::class)
//      fun answer(invocation: InvocationOnMock?): Any {
//        return choreographerMock
//      }
//    })
//    mPendingFrameCallbacks = java.util.ArrayList<ChoreographerCompat.FrameCallback>()
//    doAnswer(
//      object : Answer() {
//        @kotlin.Throws(Throwable::class)
//        fun answer(invocation: InvocationOnMock): Any? {
//          mPendingFrameCallbacks.add(
//            invocation.getArguments().get(1) as ChoreographerCompat.FrameCallback
//          )
//          return null
//        }
//      })
//      .`when`(choreographerMock)
//      .postFrameCallback(
//        any(ReactChoreographer.CallbackType::class.java),
//        any(ChoreographerCompat.FrameCallback::class.java)
//      )
//    mCatalystInstanceMock = ReactTestHelper.createMockCatalystInstance()
//    mReactContext = ReactApplicationContext(RuntimeEnvironment.getApplication())
//    mReactContext.initializeWithInstance(mCatalystInstanceMock)
//    val uiManagerModuleMock: UIManagerModule = mock(UIManagerModule::class.java)
//    `when`(mCatalystInstanceMock.getNativeModule(UIManagerModule::class.java))
//      .thenReturn(uiManagerModuleMock)
//  }
//
//  @Test
//  fun testCreateSimpleHierarchy() {
//    val uiManager: UIManagerModule = uIManagerModule
//    //
//    val rootView: ViewGroup = createSimpleTextHierarchy(uiManager, "Some text")
//    assertThat(1).isEqualTo(1)
//    val firstChild: View = rootView.getChildAt(0)
//    assertThat(firstChild).isInstanceOf(TextView::class.java)
//    assertThat((firstChild as TextView).getText().toString()).isEqualTo("Some text")
//  }
//  //
//  //  @Test
//  //  public void testUpdateSimpleHierarchy() {
//  //    UIManagerModule uiManager = getUIManagerModule();
//  //
//  //    ViewGroup rootView = createSimpleTextHierarchy(uiManager, "Some text");
//  //    TextView textView = (TextView) rootView.getChildAt(0);
//  //
//  //    int rawTextTag = 3;
//  //    uiManager.updateView(
//  //        rawTextTag,
//  //        ReactRawTextManager.REACT_CLASS,
//  //        JavaOnlyMap.of(ReactRawTextShadowNode.PROP_TEXT, "New text"));
//  //
//  //    uiManager.onBatchComplete();
//  //    executePendingFrameCallbacks();
//  //
//  //    assertThat(textView.getText().toString()).isEqualTo("New text");
//  //  }
//  //
//  //  @Test
//  //  public void testHierarchyWithView() {
//  //    UIManagerModule uiManager = getUIManagerModule();
//  //
//  //    ReactRootView rootView =
//  //        new ReactRootView(RuntimeEnvironment.getApplication().getApplicationContext());
//  //    int rootTag = uiManager.addRootView(rootView);
//  //    int viewTag = rootTag + 1;
//  //    int subViewTag = viewTag + 1;
//  //
//  //    uiManager.createView(
//  //        viewTag, ReactViewManager.REACT_CLASS, rootTag, JavaOnlyMap.of("collapsable", false));
//  //    uiManager.createView(
//  //        subViewTag, ReactViewManager.REACT_CLASS, rootTag, JavaOnlyMap.of("collapsable", false));
//  //
//  //    uiManager.manageChildren(
//  //        viewTag, null, null, JavaOnlyArray.of(subViewTag), JavaOnlyArray.of(0), null);
//  //
//  //    uiManager.manageChildren(
//  //        rootTag, null, null, JavaOnlyArray.of(viewTag), JavaOnlyArray.of(0), null);
//  //
//  //    uiManager.onBatchComplete();
//  //    executePendingFrameCallbacks();
//  //
//  //    assertThat(rootView.getChildCount()).isEqualTo(1);
//  //
//  //    ViewGroup child = (ViewGroup) rootView.getChildAt(0);
//  //    assertThat(child.getChildCount()).isEqualTo(1);
//  //
//  //    ViewGroup grandchild = (ViewGroup) child.getChildAt(0);
//  //    assertThat(grandchild).isInstanceOf(ViewGroup.class);
//  //    assertThat(grandchild.getChildCount()).isEqualTo(0);
//  //  }
//  //
//  //  @Test
//  //  public void testMoveViews() {
//  //    UIManagerModule uiManager = getUIManagerModule();
//  //    TestMoveDeleteHierarchy hierarchy = createMoveDeleteHierarchy(uiManager);
//  //
//  //    View expectedViewAt0 = hierarchy.nativeRootView.getChildAt(1);
//  //    View expectedViewAt1 = hierarchy.nativeRootView.getChildAt(2);
//  //    View expectedViewAt2 = hierarchy.nativeRootView.getChildAt(0);
//  //    View expectedViewAt3 = hierarchy.nativeRootView.getChildAt(3);
//  //
//  //    uiManager.manageChildren(
//  //        hierarchy.rootView, JavaOnlyArray.of(1, 0, 2), JavaOnlyArray.of(0, 2, 1), null, null, null);
//  //
//  //    uiManager.onBatchComplete();
//  //    executePendingFrameCallbacks();
//  //
//  //    assertChildrenAreExactly(
//  //        hierarchy.nativeRootView,
//  //        expectedViewAt0,
//  //        expectedViewAt1,
//  //        expectedViewAt2,
//  //        expectedViewAt3);
//  //  }
//  //
//  //  @Test
//  //  public void testDeleteViews() {
//  //    UIManagerModule uiManager = getUIManagerModule();
//  //    TestMoveDeleteHierarchy hierarchy = createMoveDeleteHierarchy(uiManager);
//  //
//  //    View expectedViewAt0 = hierarchy.nativeRootView.getChildAt(1);
//  //    View expectedViewAt1 = hierarchy.nativeRootView.getChildAt(2);
//  //
//  //    uiManager.manageChildren(hierarchy.rootView, null, null, null, null, JavaOnlyArray.of(0, 3));
//  //
//  //    uiManager.onBatchComplete();
//  //    executePendingFrameCallbacks();
//  //
//  //    assertChildrenAreExactly(hierarchy.nativeRootView, expectedViewAt0, expectedViewAt1);
//  //  }
//  //
//  //  @Test
//  //  public void testMoveAndDeleteViews() {
//  //    UIManagerModule uiManager = getUIManagerModule();
//  //    TestMoveDeleteHierarchy hierarchy = createMoveDeleteHierarchy(uiManager);
//  //
//  //    View expectedViewAt0 = hierarchy.nativeRootView.getChildAt(0);
//  //    View expectedViewAt1 = hierarchy.nativeRootView.getChildAt(3);
//  //    View expectedViewAt2 = hierarchy.nativeRootView.getChildAt(2);
//  //
//  //    uiManager.manageChildren(
//  //        hierarchy.rootView,
//  //        JavaOnlyArray.of(3),
//  //        JavaOnlyArray.of(1),
//  //        null,
//  //        null,
//  //        JavaOnlyArray.of(1));
//  //
//  //    uiManager.onBatchComplete();
//  //    executePendingFrameCallbacks();
//  //
//  //    assertChildrenAreExactly(
//  //        hierarchy.nativeRootView, expectedViewAt0, expectedViewAt1, expectedViewAt2);
//  //  }
//  //
//  //  @Test(expected = IllegalViewOperationException.class)
//  //  public void testMoveAndDeleteRemoveViewsDuplicateRemove() {
//  //    UIManagerModule uiManager = getUIManagerModule();
//  //    TestMoveDeleteHierarchy hierarchy = createMoveDeleteHierarchy(uiManager);
//  //
//  //    uiManager.manageChildren(
//  //        hierarchy.rootView,
//  //        JavaOnlyArray.of(3),
//  //        JavaOnlyArray.of(1),
//  //        null,
//  //        null,
//  //        JavaOnlyArray.of(3));
//  //
//  //    uiManager.onBatchComplete();
//  //    executePendingFrameCallbacks();
//  //  }
//  //
//  //  @Test(expected = IllegalViewOperationException.class)
//  //  public void testDuplicateRemove() {
//  //    UIManagerModule uiManager = getUIManagerModule();
//  //    TestMoveDeleteHierarchy hierarchy = createMoveDeleteHierarchy(uiManager);
//  //
//  //    uiManager.manageChildren(hierarchy.rootView, null, null, null, null, JavaOnlyArray.of(3, 3));
//  //
//  //    uiManager.onBatchComplete();
//  //    executePendingFrameCallbacks();
//  //  }
//  //
//  //  @Test
//  //  public void testMoveAndAddViews() {
//  //    UIManagerModule uiManager = getUIManagerModule();
//  //    TestMoveDeleteHierarchy hierarchy = createMoveDeleteHierarchy(uiManager);
//  //
//  //    int textViewTag = 1000;
//  //    uiManager.createView(
//  //        textViewTag,
//  //        ReactTextViewManager.REACT_CLASS,
//  //        hierarchy.rootView,
//  //        JavaOnlyMap.of("collapsable", false));
//  //
//  //    View expectedViewAt0 = hierarchy.nativeRootView.getChildAt(0);
//  //    View expectedViewAt1 = hierarchy.nativeRootView.getChildAt(3);
//  //    View expectedViewAt3 = hierarchy.nativeRootView.getChildAt(1);
//  //    View expectedViewAt4 = hierarchy.nativeRootView.getChildAt(2);
//  //
//  //    uiManager.manageChildren(
//  //        hierarchy.rootView,
//  //        JavaOnlyArray.of(1, 2, 3),
//  //        JavaOnlyArray.of(3, 4, 1),
//  //        JavaOnlyArray.of(textViewTag),
//  //        JavaOnlyArray.of(2),
//  //        null);
//  //
//  //    uiManager.onBatchComplete();
//  //    executePendingFrameCallbacks();
//  //
//  //    assertThat(hierarchy.nativeRootView.getChildCount()).isEqualTo(5);
//  //    assertThat(hierarchy.nativeRootView.getChildAt(0)).isEqualTo(expectedViewAt0);
//  //    assertThat(hierarchy.nativeRootView.getChildAt(1)).isEqualTo(expectedViewAt1);
//  //    assertThat(hierarchy.nativeRootView.getChildAt(3)).isEqualTo(expectedViewAt3);
//  //    assertThat(hierarchy.nativeRootView.getChildAt(4)).isEqualTo(expectedViewAt4);
//  //  }
//  //
//  //  @Test
//  //  public void testMoveViewsWithChildren() {
//  //    UIManagerModule uiManager = getUIManagerModule();
//  //    TestMoveDeleteHierarchy hierarchy = createMoveDeleteHierarchy(uiManager);
//  //
//  //    View expectedViewAt0 = hierarchy.nativeRootView.getChildAt(0);
//  //    View expectedViewAt1 = hierarchy.nativeRootView.getChildAt(2);
//  //    View expectedViewAt2 = hierarchy.nativeRootView.getChildAt(1);
//  //    View expectedViewAt3 = hierarchy.nativeRootView.getChildAt(3);
//  //
//  //    uiManager.manageChildren(
//  //        hierarchy.rootView, JavaOnlyArray.of(1, 2), JavaOnlyArray.of(2, 1), null, null, null);
//  //
//  //    uiManager.onBatchComplete();
//  //    executePendingFrameCallbacks();
//  //
//  //    assertChildrenAreExactly(
//  //        hierarchy.nativeRootView,
//  //        expectedViewAt0,
//  //        expectedViewAt1,
//  //        expectedViewAt2,
//  //        expectedViewAt3);
//  //    assertThat(((ViewGroup) hierarchy.nativeRootView.getChildAt(2)).getChildCount()).isEqualTo(2);
//  //  }
//  //
//  //  @Test
//  //  public void testDeleteViewsWithChildren() {
//  //    UIManagerModule uiManager = getUIManagerModule();
//  //    TestMoveDeleteHierarchy hierarchy = createMoveDeleteHierarchy(uiManager);
//  //
//  //    View expectedViewAt0 = hierarchy.nativeRootView.getChildAt(0);
//  //    View expectedViewAt1 = hierarchy.nativeRootView.getChildAt(2);
//  //    View expectedViewAt2 = hierarchy.nativeRootView.getChildAt(3);
//  //
//  //    uiManager.manageChildren(hierarchy.rootView, null, null, null, null, JavaOnlyArray.of(1));
//  //
//  //    uiManager.onBatchComplete();
//  //    executePendingFrameCallbacks();
//  //
//  //    assertChildrenAreExactly(
//  //        hierarchy.nativeRootView, expectedViewAt0, expectedViewAt1, expectedViewAt2);
//  //  }
//  //
//  //  @Test
//  //  public void testLayoutAppliedToNodes() throws Exception {
//  //    UIManagerModule uiManager = getUIManagerModule();
//  //
//  //    TestMoveDeleteHierarchy hierarchy = createMoveDeleteHierarchy(uiManager);
//  //
//  //    int newViewTag = 10000;
//  //    uiManager.createView(
//  //        newViewTag,
//  //        ReactViewManager.REACT_CLASS,
//  //        hierarchy.rootView,
//  //        JavaOnlyMap.of(
//  //            "left", 10.0, "top", 20.0, "width", 30.0, "height", 40.0, "collapsable", false));
//  //
//  //    uiManager.manageChildren(
//  //        hierarchy.rootView, null, null, JavaOnlyArray.of(newViewTag), JavaOnlyArray.of(4), null);
//  //
//  //    uiManager.onBatchComplete();
//  //    executePendingFrameCallbacks();
//  //
//  //    View newView = hierarchy.nativeRootView.getChildAt(4);
//  //    assertThat(newView.getLeft()).isEqualTo(10);
//  //    assertThat(newView.getTop()).isEqualTo(20);
//  //
//  //    assertThat(newView.getWidth()).isEqualTo(30);
//  //    assertThat(newView.getHeight()).isEqualTo(40);
//  //  }
//  //
//  //  /** This is to make sure we execute enqueued operations in the order given by JS. */
//  //  @Test
//  //  public void testAddUpdateRemoveInSingleBatch() {
//  //    UIManagerModule uiManager = getUIManagerModule();
//  //
//  //    TestMoveDeleteHierarchy hierarchy = createMoveDeleteHierarchy(uiManager);
//  //
//  //    int newViewTag = 10000;
//  //    uiManager.createView(
//  //        newViewTag,
//  //        ReactViewManager.REACT_CLASS,
//  //        hierarchy.rootView,
//  //        JavaOnlyMap.of("collapsable", false));
//  //
//  //    uiManager.manageChildren(
//  //        hierarchy.rootView, null, null, JavaOnlyArray.of(newViewTag), JavaOnlyArray.of(4), null);
//  //
//  //    uiManager.updateView(
//  //        newViewTag, ReactViewManager.REACT_CLASS, JavaOnlyMap.of("backgroundColor", Color.RED));
//  //
//  //    uiManager.manageChildren(hierarchy.rootView, null, null, null, null, JavaOnlyArray.of(4));
//  //
//  //    uiManager.onBatchComplete();
//  //    executePendingFrameCallbacks();
//  //
//  //    assertThat(hierarchy.nativeRootView.getChildCount()).isEqualTo(4);
//  //  }
//  //
//  //  @Test
//  //  public void testTagsAssignment() {
//  //    UIManagerModule uiManager = getUIManagerModule();
//  //    TestMoveDeleteHierarchy hierarchy = createMoveDeleteHierarchy(uiManager);
//  //
//  //    View view0 = hierarchy.nativeRootView.getChildAt(0);
//  //    assertThat(view0.getId()).isEqualTo(hierarchy.view0);
//  //
//  //    View viewWithChildren1 = hierarchy.nativeRootView.getChildAt(1);
//  //    assertThat(viewWithChildren1.getId()).isEqualTo(hierarchy.viewWithChildren1);
//  //
//  //    View childView0 = ((ViewGroup) viewWithChildren1).getChildAt(0);
//  //    assertThat(childView0.getId()).isEqualTo(hierarchy.childView0);
//  //
//  //    View childView1 = ((ViewGroup) viewWithChildren1).getChildAt(1);
//  //    assertThat(childView1.getId()).isEqualTo(hierarchy.childView1);
//  //
//  //    View view2 = hierarchy.nativeRootView.getChildAt(2);
//  //    assertThat(view2.getId()).isEqualTo(hierarchy.view2);
//  //
//  //    View view3 = hierarchy.nativeRootView.getChildAt(3);
//  //    assertThat(view3.getId()).isEqualTo(hierarchy.view3);
//  //  }
//  //
//  //  @Test
//  //  public void testLayoutPropertyUpdatingOnlyOnLayoutChange() {
//  //    UIManagerModule uiManager = getUIManagerModule();
//  //
//  //    TestMoveDeleteHierarchy hierarchy = createMoveDeleteHierarchy(uiManager);
//  //
//  //    // Update layout to some values, this way we can verify it hasn't been updated, because the
//  //    // update process would normally reset it back to some non-negative value
//  //    View view0 = hierarchy.nativeRootView.getChildAt(0);
//  //    view0.layout(1, 2, 3, 4);
//  //
//  //    // verify that X get updated when we update layout properties
//  //    uiManager.updateView(
//  //        hierarchy.view0,
//  //        ReactViewManager.REACT_CLASS,
//  //        JavaOnlyMap.of("left", 10.0, "top", 20.0, "width", 30.0, "height", 40.0));
//  //    uiManager.onBatchComplete();
//  //    executePendingFrameCallbacks();
//  //    assertThat(view0.getLeft()).isGreaterThan(2);
//  //
//  //    // verify that the layout doesn't get updated when we update style property not affecting the
//  //    // position (e.g., background-color)
//  //    view0.layout(1, 2, 3, 4);
//  //    uiManager.updateView(
//  //        hierarchy.view0,
//  //        ReactViewManager.REACT_CLASS,
//  //        JavaOnlyMap.of("backgroundColor", Color.RED));
//  //    uiManager.onBatchComplete();
//  //    executePendingFrameCallbacks();
//  //    assertThat(view0.getLeft()).isEqualTo(1);
//  //  }
//  //
//  //  /**
//  //   * Makes sure replaceExistingNonRootView by replacing a view with a new view that has a background
//  //   * color set.
//  //   */
//  //  @Test
//  //  public void testReplaceExistingNonRootView() {
//  //    UIManagerModule uiManager = getUIManagerModule();
//  //    TestMoveDeleteHierarchy hierarchy = createMoveDeleteHierarchy(uiManager);
//  //
//  //    int newViewTag = 1234;
//  //    uiManager.createView(
//  //        newViewTag,
//  //        ReactViewManager.REACT_CLASS,
//  //        hierarchy.rootView,
//  //        JavaOnlyMap.of("backgroundColor", Color.RED));
//  //
//  //    uiManager.replaceExistingNonRootView(hierarchy.view2, newViewTag);
//  //
//  //    uiManager.onBatchComplete();
//  //    executePendingFrameCallbacks();
//  //
//  //    assertThat(hierarchy.nativeRootView.getChildCount()).isEqualTo(4);
//  //    assertThat(hierarchy.nativeRootView.getChildAt(2)).isInstanceOf(ReactViewGroup.class);
//  //    ReactViewGroup view = (ReactViewGroup) hierarchy.nativeRootView.getChildAt(2);
//  //    assertThat(view.getBackgroundColor()).isEqualTo(Color.RED);
//  //  }
//  //
//  //  /**
//  //   * Verifies removeSubviewsFromContainerWithID works by adding subviews, removing them, and
//  //   * checking that the final number of children is correct.
//  //   */
//  //  @Test
//  //  public void testRemoveSubviewsFromContainerWithID() {
//  //    UIManagerModule uiManager = getUIManagerModule();
//  //    ReactRootView rootView =
//  //        new ReactRootView(RuntimeEnvironment.getApplication().getApplicationContext());
//  //    int rootTag = uiManager.addRootView(rootView);
//  //
//  //    final int containerTag = rootTag + 1;
//  //    final int containerSiblingTag = containerTag + 1;
//  //
//  //    uiManager.createView(
//  //        containerTag, ReactViewManager.REACT_CLASS, rootTag, JavaOnlyMap.of("collapsable", false));
//  //    uiManager.createView(
//  //        containerSiblingTag,
//  //        ReactViewManager.REACT_CLASS,
//  //        rootTag,
//  //        JavaOnlyMap.of("collapsable", false));
//  //    addChild(uiManager, rootTag, containerTag, 0);
//  //    addChild(uiManager, rootTag, containerSiblingTag, 1);
//  //
//  //    uiManager.createView(
//  //        containerTag + 2,
//  //        ReactTextViewManager.REACT_CLASS,
//  //        rootTag,
//  //        JavaOnlyMap.of("collapsable", false));
//  //    uiManager.createView(
//  //        containerTag + 3,
//  //        ReactTextViewManager.REACT_CLASS,
//  //        rootTag,
//  //        JavaOnlyMap.of("collapsable", false));
//  //    addChild(uiManager, containerTag, containerTag + 2, 0);
//  //    addChild(uiManager, containerTag, containerTag + 3, 1);
//  //
//  //    uiManager.onBatchComplete();
//  //    executePendingFrameCallbacks();
//  //
//  //    assertThat(rootView.getChildCount()).isEqualTo(2);
//  //    assertThat(((ViewGroup) rootView.getChildAt(0)).getChildCount()).isEqualTo(2);
//  //
//  //    uiManager.removeSubviewsFromContainerWithID(containerTag);
//  //
//  //    uiManager.onBatchComplete();
//  //    executePendingFrameCallbacks();
//  //
//  //    assertThat(rootView.getChildCount()).isEqualTo(2);
//  //    assertThat(((ViewGroup) rootView.getChildAt(0)).getChildCount()).isEqualTo(0);
//  //  }
//  /**
//   * Assuming no other views have been created, the root view will have tag 1, Text tag 2, and
//   * RawText tag 3.
//   */
//  private fun createSimpleTextHierarchy(uiManager: UIManagerModule, text: String): ViewGroup {
//    val rootView = ReactRootView(RuntimeEnvironment.getApplication().getApplicationContext())
//    val rootTag: Int = uiManager.addRootView(rootView)
//    val textTag = rootTag + 1
//    val rawTextTag = textTag + 1
//    uiManager.createView(
//      textTag, ReactTextViewManager.REACT_CLASS, rootTag, JavaOnlyMap.of("collapsable", false)
//    )
//    uiManager.createView(
//      rawTextTag,
//      ReactRawTextManager.REACT_CLASS,
//      rootTag,
//      JavaOnlyMap.of(ReactRawTextShadowNode.PROP_TEXT, text, "collapsable", false)
//    )
//    uiManager.manageChildren(
//      textTag, null, null, JavaOnlyArray.of(rawTextTag), JavaOnlyArray.of(0), null
//    )
//    uiManager.manageChildren(
//      rootTag, null, null, JavaOnlyArray.of(textTag), JavaOnlyArray.of(0), null
//    )
//    uiManager.onBatchComplete()
//    executePendingFrameCallbacks()
//    return rootView
//  }
//
//  private fun createMoveDeleteHierarchy(uiManager: UIManagerModule): TestMoveDeleteHierarchy {
//    val rootView = ReactRootView(mReactContext)
//    val rootTag: Int = uiManager.addRootView(rootView)
//    val hierarchy = TestMoveDeleteHierarchy(rootView, rootTag)
//    uiManager.createView(
//      hierarchy.view0,
//      ReactViewManager.REACT_CLASS,
//      rootTag,
//      JavaOnlyMap.of("collapsable", false)
//    )
//    uiManager.createView(
//      hierarchy.viewWithChildren1,
//      ReactViewManager.REACT_CLASS,
//      rootTag,
//      JavaOnlyMap.of("collapsable", false)
//    )
//    uiManager.createView(
//      hierarchy.view2,
//      ReactViewManager.REACT_CLASS,
//      rootTag,
//      JavaOnlyMap.of("collapsable", false)
//    )
//    uiManager.createView(
//      hierarchy.view3,
//      ReactViewManager.REACT_CLASS,
//      rootTag,
//      JavaOnlyMap.of("collapsable", false)
//    )
//    uiManager.createView(
//      hierarchy.childView0,
//      ReactViewManager.REACT_CLASS,
//      rootTag,
//      JavaOnlyMap.of("collapsable", false)
//    )
//    uiManager.createView(
//      hierarchy.childView1,
//      ReactViewManager.REACT_CLASS,
//      rootTag,
//      JavaOnlyMap.of("collapsable", false)
//    )
//    addChild(uiManager, rootTag, hierarchy.view0, 0)
//    addChild(uiManager, rootTag, hierarchy.viewWithChildren1, 1)
//    addChild(uiManager, rootTag, hierarchy.view2, 2)
//    addChild(uiManager, rootTag, hierarchy.view3, 3)
//    addChild(uiManager, hierarchy.viewWithChildren1, hierarchy.childView0, 0)
//    addChild(uiManager, hierarchy.viewWithChildren1, hierarchy.childView1, 1)
//    uiManager.onBatchComplete()
//    executePendingFrameCallbacks()
//    return hierarchy
//  }
//
//  private fun addChild(uiManager: UIManagerModule, parentTag: Int, childTag: Int, index: Int) {
//    uiManager.manageChildren(
//      parentTag, null, null, JavaOnlyArray.of(childTag), JavaOnlyArray.of(index), null
//    )
//  }
//
//  private fun assertChildrenAreExactly(parent: ViewGroup, vararg views: View) {
//    assertThat(parent.getChildCount()).isEqualTo(views.size)
//    for (i in views.indices) {
//      assertThat(parent.getChildAt(i)).describedAs("View at $i").isEqualTo(views[i])
//    }
//  }
//
//  /**
//   * Holder for the tags that represent that represent views in the following hierarchy: - View
//   * rootView - View view0 - View viewWithChildren1 - View childView0 - View childView1 - View view2
//   * - View view3
//   *
//   *
//   * This hierarchy is used to test move/delete functionality in manageChildren.
//   */
//  private class TestMoveDeleteHierarchy(nativeRootView: ReactRootView, rootViewTag: Int) {
//    var nativeRootView: ReactRootView
//    var rootView: Int
//    var view0: Int
//    var viewWithChildren1: Int
//    var view2: Int
//    var view3: Int
//    var childView0: Int
//    var childView1: Int
//
//    init {
//      this.nativeRootView = nativeRootView
//      rootView = rootViewTag
//      view0 = rootView + 1
//      viewWithChildren1 = rootView + 2
//      view2 = rootView + 3
//      view3 = rootView + 4
//      childView0 = rootView + 5
//      childView1 = rootView + 6
//    }
//  }
//
//  private fun executePendingFrameCallbacks() {
//    val callbacks: java.util.ArrayList<ChoreographerCompat.FrameCallback> =
//      java.util.ArrayList<Any?>(mPendingFrameCallbacks)
//    mPendingFrameCallbacks.clear()
//    for (frameCallback in callbacks) {
//      frameCallback.doFrame(0)
//    }
//  }
//
//  private val uIManagerModule: UIManagerModule
//    private get() {
//      val viewManagers: List<ViewManager> = Arrays.asList<ViewManager>(
//        ReactViewManager(), ReactTextViewManager(), ReactRawTextManager()
//      )
//      val uiManagerModule = UIManagerModule(mReactContext, viewManagers, 0)
//      uiManagerModule.onHostResume()
//      return uiManagerModule
//    }
//}
