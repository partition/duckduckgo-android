package com.duckduckgo.mobile.android.adapters;

import com.duckduckgo.mobile.android.R;
import com.duckduckgo.mobile.android.util.Item;
import com.duckduckgo.mobile.android.util.Item.ItemType;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.HashMap;

public class PageMenuContextAdapter extends ArrayAdapter<Item> {

  static HashMap<ItemType, Item> dialogItems;
  Context context;

  public PageMenuContextAdapter(Context context, int resource,
      int textViewResourceId) {
    super(context, resource, textViewResourceId);
    this.context = context;

    // avoid creating static item map again
    if (dialogItems == null) {
      dialogItems = new HashMap<ItemType, Item>();
      initItemMap();
    }
  }

  private void initItemMap() {/*
        dialogItems.put(ItemType.SHARE, new Item(context.getResources().getString(R.string.Share), android.R.drawable.ic_menu_share, ItemType.SHARE));
        dialogItems.put(ItemType.SAVE, new Item(context.getResources().getString(R.string.Save), android.R.drawable.ic_menu_save, ItemType.SAVE));
        dialogItems.put(ItemType.UNSAVE, new Item(context.getResources().getString(R.string.Unsave), android.R.drawable.ic_menu_delete, ItemType.UNSAVE));
        dialogItems.put(ItemType.DELETE, new Item(context.getResources().getString(R.string.Delete), android.R.drawable.ic_menu_close_clear_cancel, ItemType.DELETE));
        dialogItems.put(ItemType.EXTERNAL, new Item(context.getResources().getString(R.string.OpenInExternalBrowser), android.R.drawable.ic_menu_rotate, ItemType.EXTERNAL));
        dialogItems.put(ItemType.REFRESH, new Item(context.getResources().getString(R.string.Refresh), R.drawable.icon_reload, ItemType.REFRESH));
        dialogItems.put(ItemType.READABILITY_ON, new Item(context.getResources().getString(R.string.ReadabilityOn), R.drawable.icon_readability_on, ItemType.READABILITY_ON));
        dialogItems.put(ItemType.READABILITY_OFF, new Item(context.getResources().getString(R.string.ReadabilityOff), R.drawable.icon_readability_off, ItemType.READABILITY_OFF));*/
    dialogItems.put(ItemType.SAVE, new Item(context.getResources().getString(R.string.action_add_favorite), android.R.drawable.ic_menu_save, ItemType.SAVE));
    dialogItems.put(ItemType.UNSAVE, new Item(context.getResources().getString(R.string.action_remove_favorite), android.R.drawable.ic_menu_save, ItemType.UNSAVE));
    dialogItems.put(ItemType.SHARE, new Item(context.getResources().getString(R.string.action_share), android.R.drawable.ic_menu_save, ItemType.SHARE));
    dialogItems.put(ItemType.EXTERNAL, new Item(context.getResources().getString(R.string.action_view_external), android.R.drawable.ic_menu_save, ItemType.EXTERNAL));
  }

  protected Item getItem(ItemType itemType) {
    return dialogItems.get(itemType);
  }

  public View getView(int position, View convertView, android.view.ViewGroup parent) {
    View v = super.getView(position, convertView, parent);
    TextView tv = (TextView) v.findViewById(android.R.id.text1);
    //tv.setCompoundDrawablesWithIntrinsicBounds(getItem(position).icon, 0, 0, 0);//aaa
    //tv.setTextSize(context.getResources().getDimension(R.dimen.dialog_text_size));
    //Log.e("aaa", "%%%%%%%%%%%%%%%% - dialog tv text size: "+tv.getTextSize()+" - res value: "+getContext().getResources().getDimension(R.dimen.dialog_text_size));
    //Log.e("aaa", "%%%%%%%%%%%%%%%% - dialog textappearance")
    //tv.setTextSize(tv.getTextSize());
    //float textSize = tv.getTextSize();
    //textSize = textSize*0.5f;
    //tv.setTextSize(textSize);
    //tv.setPadding(tv.getPaddingLeft(), tv.getPaddingTop(), tv.getPaddingRight(), tv.getPaddingBottom());
    //tv.setTextSize(getContext().getResources().getDimension(R.dimen.dialog_text_size));
    //tv.setTextAppearance(context, android.R.style.TextAppearance_DeviceDefault_Medium);

    //Add 10dp margin between image and text (support various screen densities)
    int dp10 = (int) (10 * context.getResources().getDisplayMetrics().density + 0.5f);
    //tv.setCompoundDrawablePadding(dp10);

    return v;
  }
}
