package app.Buyer.account;

import org.openqa.selenium.By;

public class BuyerMyProfileElement {
    By MY_PROFILE_HEADER_TITLE = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_toolbar_title')]");
    By MY_PROFILE_HEADER_SAVE_BTN = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_btn_save')]");
    By AVATAR = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_avatar')]");
    By YOUR_NAME_LBL = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_tv_fullname_notice')]/preceding-sibling::android.widget.TextView");
    By YOUR_NAME_INPUT = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_tv_et_yourname')]");
    By EMAIL_LBL = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_email_container')]//android.widget.TextView");
    By EMAIL_INPUT = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_tv_et_email')]");
    By IDENTITY_CARD_LBL = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_tv_et_identity')]/parent::android.widget.RelativeLayout/preceding-sibling::android.widget.TextView");
    By IDENTITY_CARD_INPUT = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_tv_et_identity')]");
    By OTHER_EMAILS_LBL = By.xpath("//*[ends-with(@resource-id,'flOtherEmailContainer')]/android.widget.RelativeLayout[1]/android.widget.TextView");
    By YOU_HAVE_OTHER_MAIL_LBL = By.xpath("//*[ends-with(@resource-id,'tvOtherEmailCount')]");
    By PHONE_LBL = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_phone_container')]/preceding-sibling::android.widget.RelativeLayout/android.widget.TextView");
    By COUNTRY_NAME = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_tv_country_name')]");
    By PHONE_CODE = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_tv_country_phone_code')]");
    By PHONE_NUMBER_INPUT = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_tv_et_phone')]");
    By OTHER_PHONE_LBL = By.xpath("//*[ends-with(@resource-id,'flOtherPhoneNumberContainer')]/android.widget.RelativeLayout[1]/android.widget.TextView");
    By YOUR_HAVE_OTHER_PHONE_LBL = By.xpath("//*[ends-with(@resource-id,'tvOtherPhoneNumberCount')]");
    By COMPANY_NAME_INPUT = By.xpath("//*[ends-with(@resource-id,'edt_company_name')]");
    By COMPANY_NAME_LBL = By.xpath("//*[ends-with(@resource-id,'edt_company_name')]/preceding-sibling::android.widget.TextView");
    By TAX_CODE_LBL = By.xpath("//*[ends-with(@resource-id,'edt_tax_code')]/preceding-sibling::android.widget.TextView");
    By TAX_CODE_INPUT = By.xpath("//*[ends-with(@resource-id,'edt_tax_code')]");
    By PROFILE_ADDRESS_LBL = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_address_container')]/preceding-sibling::android.widget.RelativeLayout/android.widget.TextView");
    By ADDRESS_TXT = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_tv_et_address')]");
    By GENDER_LBL = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_birthday_container')]/preceding-sibling::android.widget.TextView");
    By MAN_OPTION = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_st_user_gender')]/android.widget.TextView[1]");
    By WOMAN_OPTION = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_st_user_gender')]/android.widget.TextView[2]");
    By BIRTHDAY_INPUT = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_tv_et_birthday')]");
    By BIRTHDAY_LBL = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_tv_et_birthday')]/parent::android.widget.RelativeLayout/preceding-sibling::android.widget.TextView");
    By CHANGE_PASSWORD_LBl = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_tv_change_password')]");
    By DELETE_ACCOUNT_LBL = By.xpath("//*[ends-with(@resource-id,'tvDeleteAccount')]");
    By ADDRESS_HEADER_TITLE = By.xpath("//*[ends-with(@resource-id,'activity_edit_address_toolbar_title')]");
    By ADDRESS_HEADER_SAVE_BTN = By.xpath("//*[ends-with(@resource-id,'activity_edit_address_btn_save')]");
    By COUNTRY_LBL = By.xpath("//*[ends-with(@resource-id,'activity_edit_address_ll_input_country')]/android.widget.TextView");
    By COUNTRY_DROPDOWN = By.xpath("//*[ends-with(@resource-id,'activity_edit_address_et_country')]");
    By ADDRESS_LBL = By.xpath("//*[ends-with(@resource-id,'activity_edit_address_tv_address_text_count')]/preceding-sibling::android.widget.TextView");
    By ADDRESS_INPUT = By.xpath("//*[ends-with(@resource-id,'activity_edit_address_et_address')]");
    By CITY_PROVINCE_LBL = By.xpath("//*[ends-with(@resource-id,'activity_edit_address_ll_input_city')]/android.widget.TextView");
    By CITY_PROVINCE_DROPDOWN = By.xpath("//*[ends-with(@resource-id,'activity_edit_address_et_city')]");
    By DISTRICT_LBL = By.xpath("//*[ends-with(@resource-id,'activity_edit_address_ll_input_district')]/android.widget.TextView");
    By DISTRICT_DROPDOWN = By.xpath("//*[ends-with(@resource-id,'activity_edit_address_et_district')]");
    By WARD_LBL = By.xpath("//*[ends-with(@resource-id,'activity_edit_address_ll_input_ward')]/android.widget.TextView");
    By WARD_DROPDOWN = By.xpath("//*[ends-with(@resource-id,'activity_edit_address_et_ward')]");
    By ADDRESS_2_LBL = By.xpath("//*[ends-with(@resource-id,'activity_edit_address_tv_address2_text_count')]/preceding-sibling::android.widget.TextView");
    By ADDRESS_2_INPUT = By.xpath("//*[ends-with(@resource-id,'activity_edit_address_et_address2')]");
    By STATE_REGION_PROVINCE_LBL = By.xpath("//*[ends-with(@resource-id,'activity_edit_address_ll_input_state')]/android.widget.TextView");
    By STAT_REGION_PROVINCE_DROPDOWN = By.xpath("//*[ends-with(@resource-id,'activity_edit_address_et_state')]");
    By CITY_LBL = By.xpath("//*[ends-with(@resource-id,'activity_edit_address_tv_city_outside_vietnam_text_count')]/preceding-sibling::android.widget.TextView");
    By CITY_INPUT = By.xpath("//*[ends-with(@resource-id,'activity_edit_address_et_city_outside_vietnam')]");
    By ZIP_CODE_LBL = By.xpath("//*[ends-with(@resource-id,'activity_edit_address_tv_zip_code_text_count')]/preceding-sibling::android.widget.TextView");
    By OTHER_EMAIL_TITLE = By.xpath("//*[ends-with(@resource-id,'activity_menu_collection_product_action_bar_basic_title')]");
    By OTHER_EMAIL_ADD_ICON = By.xpath("//*[ends-with(@resource-id,'action_bar_basic_right_button_icon_2')]");
    By OTHER_EMAIL_BACK_ICON = By.xpath("//*[ends-with(@resource-id,'action_bar_basic_img_back')]");
    By OTHER_EMAIL_POPUP_TITLE = By.xpath("//*[ends-with(@resource-id,'tvTitle')]");
    By OTHER_EMAIL_INPUT_NAME = By.xpath("//*[ends-with(@resource-id,'edtName')]");
    By OTHER_EMAIL_INPUT_EMAIL = By.xpath("//*[ends-with(@resource-id,'edtEmail')]");
    By OTHER_EMAIL_ADD_BTN = By.xpath("//*[ends-with(@resource-id,'tvAdd')]");
    By OTHER_EMAIL_POPUP_CLOSE_ICON = By.xpath("//*[ends-with(@resource-id,'ivClose')]");
    By OTHER_PHONE_TITLE = By.xpath("//*[ends-with(@resource-id,'activity_menu_collection_product_action_bar_basic_title')]");
    By OTHER_PHONE_ADD_ICON = By.xpath("//*[ends-with(@resource-id,'action_bar_basic_right_button_icon_2')]");
    By OTHER_PHONE_BACK_ICON = By.xpath("//*[ends-with(@resource-id,'action_bar_basic_img_back')]");
    By OTHER_PHONE_POPUP_TITLE = By.xpath("//*[ends-with(@resource-id,'tvTitle')]");
    By OTHER_PHONE_INPUT_NAME = By.xpath("//*[ends-with(@resource-id,'edtName')]");
    By OTHER_PHONE_INPUT_PHONE = By.xpath("//*[ends-with(@resource-id,'edtPhoneNumber')]");
    By OTHER_PHONE_PHONE_CODE = By.xpath("//*[ends-with(@resource-id,'tvPhoneCode')]");
    By OTHER_PHONE_PHONE_CODE_LIST = By.xpath("//*[ends-with(@resource-id,'item_list_region_name')]");
    By OTHER_PHONE_ADD_BTN = By.xpath("//*[ends-with(@resource-id,'tvAdd')]");
    By OTHER_PHONE_POPUP_CLOSE_ICON = By.xpath("//*[ends-with(@resource-id,'ivClose')]");
    By OTHER_PHONE_EMAIL_NAME_LIST = By.xpath("//*[ends-with(@resource-id,'tvTitle')]");
    By OTHER_PHONE_EMAIL_LIST = By.xpath("//*[ends-with(@resource-id,'tvValue')]");
    By OTHER_PHONE_EMAIL_DELETE_ICON = By.xpath("//*[ends-with(@resource-id,'rlDelete')]//android.widget.LinearLayout");
    By COUNTRY_HEADER_TITLE = By.xpath("//*[ends-with(@resource-id,'fragment_select_region_title')]");
    By COUNTRY_CLOSE_ICON = By.xpath("//*[ends-with(@resource-id,'fragment_select_region_btn_close')]");
    By COUNTRY_LIST = By.xpath("//*[ends-with(@resource-id,'item_list_region_name')]");
    By COUNTRY_CODE_HEADER_TITLE = By.xpath("//*[ends-with(@resource-id,'fragment_choose_country_code_list_layout_tv_title')]");
    By COUNTRY_CODE_HEADER_BACK_ICON = By.xpath("//*[ends-with(@resource-id,'fragment_choose_country_code_list_layout_toolbar')]/android.widget.ImageButton[1]");
    By COUNTRY_CODE_HEADER_SEARCH_ICON = By.xpath("//*[ends-with(@resource-id,'fragment_choose_country_code_list_layout_btn_search')]");
    By COUNTRY_CODE_SEARCH_INPUT = By.xpath("//*[ends-with(@resource-id,'search_src_text')]");
    By COUNTRY_CODE_LIST = By.xpath("//*[ends-with(@resource-id,'item_choose_country_code_list_tv_code')]");
    By DATE_PICKER_OK_BTN = By.xpath("//*[ends-with(@resource-id,'ok')]");
    By HEADER_BACK_ICON = By.xpath("//*[ends-with(@resource-id,'fragment_general_edit_beecow_profile_toolbar')]/android.widget.ImageButton");
    By ADD_OTHER_PHONE_ERROR = By.xpath("//*[ends-with(@resource-id,'tvErrorPhoneNumber')]");
    By ADD_OTHER_EMAIL_ERROR = By.xpath("//*[ends-with(@resource-id,'tvErrorEmail')]");
    By CURRENT_CHANGEPASSWORD = By.xpath("//*[ends-with(@resource-id,'current_password_field')]//*[ends-with(@resource-id,'social_layout_limit_edittext')]");
    By NEW_CHANGEPASSWORD = By.xpath("//*[ends-with(@resource-id,'new_password_field')]//*[ends-with(@resource-id,'social_layout_limit_edittext')]");
    By CHANGEPASSWORD_DONE_BTN = By.xpath("//*[ends-with(@resource-id,'change_password_btn_done')]");
    By CURRENT_CHANGEPASSWORD_ERROR = By.xpath("//*[ends-with(@resource-id,'change_password_tv_error_current_password')]");
    By NEW_CHANGEPASSWORD_ERROR = By.xpath("//*[ends-with(@resource-id,'change_password_tv_error_new_password')]");
    By DELETE_ACCOUNT_POPUP_TITLE = By.xpath("//*[ends-with(@resource-id,'fragment_dialog_confirmation_tv_title')]");
    By DELETE_ACCOUNT_POPUP_MESSAGE = By.xpath("//*[ends-with(@resource-id,'fragment_dialog_confirmation_tv_description')]");
    By DELETE_ACCOUNT_POPUP_DELETE_BTN = By.xpath("//*[ends-with(@resource-id,'fragment_dialog_confirmation_tv_positive_action')]");
    By DELETE_ACCOUNT_POPUP_CANCEL_BTN = By.xpath("//*[ends-with(@resource-id,'fragment_dialog_confirmation_tv_negative_action')]");

}
