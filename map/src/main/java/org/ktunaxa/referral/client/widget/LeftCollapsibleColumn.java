package org.ktunaxa.referral.client.widget;

import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Column which can be collapsed (to the left).
 *
 * @author Joachim Van der Auwera
 */
public class LeftCollapsibleColumn
    extends VLayout
{

    private Label leftTitle;
    private VLayout leftMiddleLayout;
    private Canvas leftContent;

    public LeftCollapsibleColumn( int width )
    {
        super();
        this.setWidth( width );
        this.setHeight100();

        LayoutSpacer spacer = new LayoutSpacer();
        spacer.setHeight( 5 );
        this.addMember( spacer );

        HLayout top = new HLayout();
        top.setHeight( 30 );
        top.setBackgroundImage( "[ISOMORPHIC]/images/left_header.png" );

        leftTitle = new Label();
        leftTitle.setWidth( width - 95 );
        leftTitle.setHeight( 10 );
        leftTitle.setTop( 10 );
        leftTitle.setLeft( 20 );
        top.addChild( leftTitle );

        Img hideImg = new Img( "[ISOMORPHIC]/images/left_hide.png" );
        hideImg.setWidth( 15 );
        hideImg.setHeight( 15 );
        hideImg.setTop( 10 );
        hideImg.setLeft( width - 25 );
        hideImg.setCursor( Cursor.HAND );
        hideImg.setTooltip( "Hide this panel" );
        hideImg.addClickHandler( new ClickHandler()
        {

            public void onClick( ClickEvent event )
            {
                close();
            }
        } );
        top.addChild( hideImg );

        this.addMember( top );

        leftMiddleLayout = new VLayout();
        leftMiddleLayout.setWidth100();
        leftMiddleLayout.setHeight100();
        leftMiddleLayout.setStyleName( "lccContent" );
        leftMiddleLayout.setBackgroundImage( "[ISOMORPHIC]/images/left_bg.png" );
        this.addMember( leftMiddleLayout );

        HLayout bottom = new HLayout();
        bottom.setHeight( 11 );
        bottom.setBackgroundImage( "[ISOMORPHIC]/images/left_footer.png" );
        this.addMember( bottom );

        close();
    }

    public void close()
    {
        this.hide();
    }

    public void show( String title, Canvas canvas )
    {
        if ( leftContent != null && leftContent != canvas )
        {
            leftMiddleLayout.removeMember( leftContent );
            leftContent = canvas;
            leftMiddleLayout.addMember( canvas );
        }
        else if ( leftContent == null )
        {
            leftContent = canvas;
            leftMiddleLayout.addMember( canvas );
        }
        leftTitle.setContents( "<div style='font-size:14px; font-weight:bold;'>" + title + "</div>" );
        this.show();
    }

}
