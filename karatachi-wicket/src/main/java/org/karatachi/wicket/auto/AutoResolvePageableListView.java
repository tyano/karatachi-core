package org.karatachi.wicket.auto;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.resolver.IComponentResolver;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

public class AutoResolvePageableListView<T> extends PageableListView<T>
        implements IComponentResolver {
    private static final long serialVersionUID = 1L;

    public static String EVEN = "even";
    public static String ODD = "odd";

    public AutoResolvePageableListView(String id, int rowsPerPage) {
        this(id, (IModel<List<T>>) null, rowsPerPage);
    }

    public AutoResolvePageableListView(String id, List<T> data, int rowsPerPage) {
        super(id, data, rowsPerPage);
    }

    public AutoResolvePageableListView(String id, IModel<List<T>> model,
            int rowsPerPage) {
        super(id, model, rowsPerPage);
    }

    @Override
    protected ListItem<T> newItem(int index, IModel<T> itemModel) {
        final ListItem<T> ret = super.newItem(index, itemModel);
        ret.add(new AttributeAppender("class",
                new AbstractReadOnlyModel<String>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public String getObject() {
                        return getClassAttribute(ret.getId(), ret.getIndex(),
                                ret.getModelObject());
                    }
                }, " "));
        return ret;
    }

    protected String getClassAttribute(String id, int index, T modelObject) {
        return (index % 2) == 0 ? EVEN : ODD;
    }

    @Override
    protected IModel<T> getListItemModel(
            IModel<? extends List<T>> listViewModel, int index) {
        return new CompoundPropertyModel<T>(super.getListItemModel(
                listViewModel, index));
    }

    public Component resolve(MarkupContainer container,
            MarkupStream markupStream, ComponentTag tag) {
        if (tag.isAutoComponentTag()) {
            return null;
        }

        String tagId = tag.getId();
        String type = tag.getAttributes().getString("type");

        String wicketType = null;
        if (tag.getAttributes().containsKey("wicket:type")) {
            String[] wicketTypeValue =
                    tag.getAttributes().getString("wicket:type").split(":");
            wicketType = wicketTypeValue[0];
        }

        FormComponentResolver resolver =
                FormComponentResolver.getResolver(wicketType, type);

        if (resolver != null) {
            return resolver.createViewComponent(tagId);
        } else {
            return FormComponentResolver.getDefaultResolver().createViewComponent(
                    tagId);
        }
    }

    @Override
    protected void populateItem(final ListItem<T> item) {
        ;
    }
}
