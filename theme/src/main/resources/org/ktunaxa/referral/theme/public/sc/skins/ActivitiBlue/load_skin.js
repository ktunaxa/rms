/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

isc.loadSkin = function (theWindow) {
    if (theWindow == null) theWindow = window;
    with (theWindow) {

        isc.Page.setSkinDir("[ISOMORPHIC]/skins/ActivitiBlue/");
        isc.Page.loadStyleSheet("[SKIN]/skin_styles.css", theWindow);
        isc.Page.loadStyleSheet("[SKIN]/ktunaxa_specific.css", theWindow);

        isc.Canvas.setProperties({
            // this skin uses custom scrollbars
            showCustomScrollbars:isc.Browser.isMobile,
            groupBorderCSS :"1px solid #165fa7"
        });

        if(isc.Browser.isIE && isc.Browser.version >= 7) {
            isc.Canvas.setAllowExternalFilters(false);
            isc.Canvas.setNeverUseFilters(true);
            if(isc.Window) {
              isc.Window.addProperties({
                    modalMaskOpacity:null,
                    modalMaskStyle:"normal"
                });
                isc.Window.changeDefaults("modalMaskDefaults", { src : "[SKIN]opacity.png" });
            }
        }

        if(isc.RPCManager) {
            isc.RPCManager.addClassProperties({ promptStyle:"cursor" });
        }
        isc.Button.addProperties({
            height: 23
        });

        // define IButton so examples that support the new SmartClient skin image-based
        // button will fall back on the CSS-based Button with this skin
        isc.ClassFactory.defineClass("IButton", "Button");
        isc.ClassFactory.defineClass("IAutoFitButton", "AutoFitButton");
        if (isc.IButton.markAsFrameworkClass != null) isc.IButton.markAsFrameworkClass();
        if (isc.IAutoFitButton.markAsFrameworkClass != null) isc.IAutoFitButton.markAsFrameworkClass();

        isc.ClassFactory.defineClass("HeaderMenuButton", "IButton").addProperties({
            baseStyle: "headerButton"
        });

        // Have IMenuButton be just a synonym for IMenuButton
        if (isc.MenuButton) {
            isc.ClassFactory.defineClass("IMenuButton", "MenuButton");
            if (isc.IMenuButton.markAsFrameworkClass != null) isc.IMenuButton.markAsFrameworkClass();
            isc.MenuButton.addProperties({
                // copy the header (.button) background-color to match when sort arrow is hidden
                baseStyle : "button"
            });
        }

        if (isc.PickTreeItem) {
            isc.defineClass("IPickTreeItem", "PickTreeItem");
        }

        isc.Label.addProperties({
            showFocused: false
        });

        //----------------------------------------
        // 3) Resizebars
        //----------------------------------------
        // StretchImgSplitbar class renders as resize bar
        isc.StretchImgSplitbar.addProperties({
            capSize:10,
            showGrip:true,
            showOver : false
        });

        isc.Snapbar.addProperties({
            vSrc:"[SKIN]vsplit.gif",
            hSrc:"[SKIN]hsplit.gif",
            baseStyle:"splitbar",
            items : [
                {name:"blank", width:"capSize", height:"capSize"},
                {name:"blank", width:"*", height:"*"},
                {name:"blank", width:"capSize", height:"capSize"}
            ],
            showDownGrip:false,
            gripBreadth:5,
            gripLength:35,
            capSize:0,
            showRollOver : false,
            showDown : false
        });

        isc.Layout.addProperties({
            resizeBarSize:9,
            // Use the Snapbar as a resizeBar by default - subclass of Splitbar that
            // shows interactive (closed/open) grip images
            // Other options include the Splitbar, StretchImgSplitbar or ImgSplitbar
            resizeBarClass:"Snapbar"
        })

        if (isc.SectionItem) {
            isc.SectionItem.addProperties({
                height:26
            });
        }
        if (isc.SectionStack) {
            isc.SectionStack.addProperties({
                headerHeight:26
            });
        }

        if (isc.ListGrid) {
            isc.ListGrid.addProperties({
                alternateRecordStyles : true,
                alternateBodyStyleName : null,
                editFailedCSSText:"color:FF6347;",
                errorIconSrc : "[SKINIMG]actions/exclamation.png",
                tallBaseStyle: "tallCell",
                backgroundColor:"#e7e7e7",
                headerBackgroundColor:null,
                expansionFieldImageWidth : 16,
                expansionFieldImageHeight : 16,
                headerBaseStyle : "headerButton",
                headerHeight:25,
                summaryRowHeight:21,
                cellHeight:22,
                normalCellHeight:22,
                showHeaderMenuButton:true,
                headerMenuButtonConstructor:"HeaderMenuButton",
                headerMenuButtonWidth:17,

                groupLeadingIndent : 1,
                groupIconPadding : 3,
                groupIcon: "[SKINIMG]/ListGrid/group.gif",

                expansionFieldTrueImage : "[SKINIMG]/ListGrid/row_expanded.gif",
                expansionFieldFalseImage: "[SKINIMG]/ListGrid/row_collapsed.gif",
                checkboxFieldImageWidth : 13,
                checkboxFieldImageHeight : 13
            });
        }

        if (isc.TreeGrid) {
            isc.TreeGrid.addProperties({
                alternateRecordStyles : false,
                tallBaseStyle: "treeTallCell",
                normalBaseStyle: "treeCell",
                openerIconSize: 22,
                sortAscendingImage:{src:"[SKINIMG]ListGrid/sort_ascending.gif", width:7, height:7},
                sortDescendingImage:{src:"[SKINIMG]ListGrid/sort_descending.gif", width:7, height:7}
            })
        }

        if (isc.TabSet) {
            isc.TabSet.addProperties({
                useSimpleTabs : true,
                paneMargin:5,
                closeTabIcon:"[SKIN]/TabSet/close.gif",
                closeTabIconSize:11,
                scrollerSrc:"[SKIN]scroll.gif",
                pickerButtonSrc:"[SKIN]picker.gif",
                scrollerButtonSize:16,
                pickerButtonSize:16,
                tabBarThickness:24,
                iconOrientation:"right",
                showScrollerRollOver: false
            });

            // In Netscape Navigator 4.7x, set the backgroundColor directly since the css
            // background colors are not reliable
            if (isc.Browser.isNav) {
                isc.TabSet.addProperties({paneContainerDefaults:{backgroundColor:"#FFFFFF"}});
            }

            isc.TabBar.addProperties({
                leadingMargin:5,
                membersMargin:2,

                // keep the tabs from reaching the curved edge of the pane (regardless of align)
                layoutStartMargin:5,
                layoutEndMargin:5,

                styleName:"tabBar",
                leftStyleName:"tabBarLeft",
                topStyleName:"tabBarTop",
                rightStyleName:"tabBarRight",
                bottomStyleName:"tabBarBottom",

                baseLineConstructor:"Canvas",
                baseLineProperties : {
                    backgroundColor: "#C0C3C7",
                    overflow:"hidden"
                },
                baseLineThickness:1
            });
        }

        if (isc.ImgTab) isc.ImgTab.addProperties({capSize:7});

        if (isc.Window) {
            isc.Window.addProperties({
                showHeaderBackground: false,
                backgroundColor:"#FEFEFE",
                showFooter:false,
                membersMargin : 0,
                modalMaskOpacity : 10
            });
            isc.Window.changeDefaults("headerDefaults", {
                height:25,
                layoutMargin:0,
                membersMargin:0
            });
            isc.Window.changeDefaults("resizerDefaults", {
                src:"[SKIN]/Window/resizer.gif"
            });
            isc.Window.changeDefaults("headerIconDefaults", {
                width:15,
                height:15,
                src:"[SKIN]/Window/headerIcon.gif"
            });
            isc.Window.changeDefaults("restoreButtonDefaults", {
                src:"[SKIN]/headerIcons/cascade.gif",
                showRollOver:true,
                showDown:false,
                width:15,
                height:15
            });
            isc.Window.changeDefaults("closeButtonDefaults", {
                src:"[SKIN]/headerIcons/close.gif",
                showRollOver:true,
                showDown:false,
                width:15,
                height:15
            });
            isc.Window.changeDefaults("maximizeButtonDefaults", {
                src:"[SKIN]/headerIcons/maximize.gif",
                showRollOver:true,
                width:15,
                height:15
            });
            isc.Window.changeDefaults("minimizeButtonDefaults", {
                src:"[SKIN]/headerIcons/minimize.gif",
                showRollOver:true,
                showDown:false,
                width:15,
                height:15
            });
            isc.Window.changeDefaults("toolbarDefaults", {
                buttonConstructor: "IButton"
            }) ;
            
            if (isc.ColorPicker) {
                isc.ColorPicker.addProperties({
                    layoutMargin:2
                })
            }       
        }

        if (isc.Dialog) {
            isc.Dialog.addProperties({
                bodyColor: "#f6f6f6"
            });
        }

        // Dynamic form skinning
        if (isc.FormItem) {
            isc.FormItem.addProperties({
                defaultIconSrc:"[SKIN]/DynamicForm/default_formItem_icon.gif",
                errorIconSrc : "[SKINIMG]actions/exclamation.png",
                iconHeight:18,
                iconWidth:18,
                iconVAlign:"middle"
            });
        }
        if (isc.TextItem) {
            isc.TextItem.addProperties({
                height:22,
                showFocused: true
            });
        }

        if (isc.TextAreaItem) {
            isc.TextAreaItem.addProperties({
                showFocused: true
            });
        }

        if (isc.SelectItem) {
            isc.SelectItem.addProperties({
                textBoxStyle:"selectItemText",
                showFocusedPickerIcon:false,
                pickerIconSrc:"[SKIN]/pickers/comboBoxPicker.gif",
                height:22,
                pickerIconWidth:18
            });
        }

        if (isc.ComboBoxItem) {
            isc.ComboBoxItem.addProperties({
                textBoxStyle:"selectItemText",
                showFocusedPickerIcon:false,
                pickerIconSrc:"[SKIN]/pickers/comboBoxPicker.gif",
                height:22,
                pickerIconWidth:18
            });
        }

        // used by SelectItem and ComboBoxItem for picklist
        if (isc.ScrollingMenu) {
            isc.ScrollingMenu.addProperties({
                showShadow:false,
                shadowDepth:5
            });
        }
        if (isc.DateItem) {
            isc.DateItem.addProperties({
                height:22,
                pickerIconWidth:16,
                pickerIconHeight:16,
                pickerIconSrc:"[SKIN]/DynamicForm/date_control.png"
            });
        }

        if (isc.SpinnerItem) {
            isc.SpinnerItem.addProperties({
                textBoxStyle:"selectItemText",
                height:22
            });
            isc.SpinnerItem.INCREASE_ICON = isc.addProperties(isc.SpinnerItem.INCREASE_ICON, {
                width:16,
                height:11,
                showRollOver:false,
                showFocused:false,
                showDown:false,
                imgOnly:true,
                src:"[SKIN]/DynamicForm/spinner_control_increase.png"
            });
            isc.SpinnerItem.DECREASE_ICON = isc.addProperties(isc.SpinnerItem.DECREASE_ICON, {
                width:16,
                height:11,
                showRollOver:false,
                showFocused:false,
                showDown:false,
                imgOnly:true,
                src:"[SKIN]/DynamicForm/spinner_control_decrease.png"
            });
        }
        if (isc.PopUpTextAreaItem) {
            isc.PopUpTextAreaItem.addProperties({
                popUpIconSrc: "[SKIN]/DynamicForm/text_control.gif",
                popUpIconWidth:16,
                popUpIconHeight:16
            });
        }

        if (isc.ToolbarItem && isc.IAutoFitButton) {
            isc.ToolbarItem.addProperties({
                buttonConstructor:isc.IAutoFitButton,
                buttonProperties: {
                    autoFitDirection: isc.Canvas.BOTH
                }
            });
        }

        if (isc.DateRangeDialog) {
            isc.DateRangeDialog.changeDefaults("headerIconProperties", {
                src: "[SKIN]/DynamicForm/date_control.png"
            });
        }
        if (isc.MiniDateRangeItem) {
            isc.MiniDateRangeItem.changeDefaults("pickerIconDefaults", {
                src: "[SKIN]/DynamicForm/date_control.png"
            });
        }
        if (isc.RelativeDateItem) {
            isc.RelativeDateItem.changeDefaults("pickerIconDefaults", {
                src: "[SKIN]/DynamicForm/date_control.png"
            });
        }

        // Native FILE INPUT items are rendered differently in Safari from other browsers
        // Don't show standard textbox styling around them as it looks odd
        if (isc.UploadItem && isc.Browser.isSafari) {
            isc.UploadItem.addProperties({
                textBoxStyle:"normal"
            });
        }

        if (isc.DateChooser) {
            isc.DateChooser.addProperties({
                showDoubleYearIcon:false,
                skinImgDir:"images/DateChooser/",
                headerStyle:"dateChooserButton",
                weekendHeaderStyle:"dateChooserWeekendButton",
                baseNavButtonStyle:"dateChooserNavButton",
                baseWeekdayStyle:"dateChooserWeekday",
                baseWeekendStyle:"dateChooserWeekend",
                baseBottomButtonStyle:"dateChooserBottomButton",
                alternateWeekStyles:false,
                todayButtonHeight:20,
                edgeCenterBackgroundColor:"#FFFFFF",
                backgroundColor:"#FFFFFF",
                border:"1px solid #868686"

            });
        }

        if (isc.ToolStrip) {
            isc.ToolStrip.addProperties({
                width: 450,
                height:30,
                defaultLayoutAlign:"center"
            });
            isc.ToolStripResizer.addProperties({
                backgroundColor:"#f6f6f6"
            });

            isc.ToolStrip.changeDefaults("formWrapperDefaults",{cellPadding:3});
        }

        if (isc.ToolStripMenuButton) {
            isc.defineClass("ToolStripMenuButton", "MenuButton").addProperties({
                showTitle:false,
                showRollOver:true,
                showDown:true,
                labelVPad:0,
                //labelHPad:7,
                autoFit:true,
                baseStyle : "toolbarButton",
                height:22
            });
        }

        if (isc.ToolStripButton) {
            isc.defineClass("ToolStripButton", "Button").addProperties({
                showTitle:false,
                title:null,
                showRollOver:true,
                showDown:true,
                labelVPad:0,
                //labelHPad:7,
                autoFit:true,
                baseStyle : "toolbarButton",
                height:22
            });
        }

        // Default EdgedCanvas skinning (for any canvas where showEdges is set to true)
        if (isc.EdgedCanvas) {
            isc.EdgedCanvas.addProperties({
                edgeSize:6,
                edgeImage: "[SKINIMG]edges/edge.png"
            });
        }

        if (isc.Slider) {
            isc.Slider.addProperties({
                thumbThickWidth:17,
                thumbThinWidth:11,
                trackWidth:5,
                trackCapSize:2
            });
        }

        if (isc.TileGrid) {
            isc.TileGrid.addProperties({
                valuesShowRollOver: true,
                styleName:null,
                showEdges:false
            });
        }

        if (isc.Calendar) {
            isc.Calendar.changeDefaults("datePickerButtonDefaults", {
                showDown:false,
                showOver : false,
                src:"[SKIN]/DynamicForm/date_control.png"
            });

            isc.Calendar.changeDefaults("controlsBarDefaults", {
                height:10,
                layoutBottomMargin :10
            });
        }

        if (isc.Hover) {
            isc.addProperties(isc.Hover.hoverCanvasDefaults, {
                showShadow:false,
                shadowDepth:5
            })
        }

        //indicate type of media used for various icon types
        isc.pickerImgType = "gif";
        isc.transferImgType = "gif";
        isc.headerImgType = "gif";

        isc.Page.checkBrowserAndRedirect("[SKIN]/unsupported_browser.html");
    }
}


// call the loadSkin routine
isc.loadSkin();

