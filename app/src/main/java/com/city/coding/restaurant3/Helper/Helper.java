package com.city.coding.restaurant3.Helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.city.coding.restaurant3.Activites.login;
import com.city.coding.restaurant3.Activites.signUp_entry;
import com.city.coding.restaurant3.Model.voucherData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Helper {
    private static String TAG = "Helper";
    //static section
    //male
    private static final int MALE = 1;
    //female
    private static final int FEMALE = 2;
    //Other
    private static final int OTHER = 3;
    //end

    //reg new user url
    public static final String Url ="http://honeydewpos.com/loycher/api/reg_new_user.php";


    // check the gender of the client
    public static String myGender(int gender) {
        //check gender male=1 , female=2 , other=3
        if (gender == MALE)
            return "Male";
        else if (gender == FEMALE)
            return "Female";
        else
            return "Other";
    }
    //end myGender

    //method to capitalize the first Letter in a text
    public static String firstLetterCap(String str) {
        //first remove edge space using trim() fun
        //and then split all the text using split() fun
        String strArr[] = str.trim().split(" ");
        //create string builder object
        StringBuilder output = new StringBuilder();
        //loop through strArr and capital the first letter
        //append in each loop until exiting
        for (int i = 0; i < strArr.length; i++) {   //sub the element and capitalize it
            String ele = strArr[i].substring(0, 1).toUpperCase();
            //append it to stringBuilder with the rest of the String
            output.append(ele + strArr[i].substring(1));
            //add space
            output.append(" ");
        }
        //return the output as string
        return output.toString();
    }

    public static String term = "Home\n" +
            "1. YOUR AGREEMENT\n" +
            "By using this Site, you agree to be bound by, and to comply with, these Terms and Conditions. If you do not agree to these Terms and Conditions, please do not use this site.\n" +
            "\n" +
            "PLEASE NOTE: We reserve the right, at our sole discretion, to change, modify or otherwise alter these Terms and Conditions at any time. Unless otherwise indicated, amendments will become effective immediately. Please review these Terms and Conditions periodically. Your continued use of the Site following the posting of changes and/or modifications will constitute your acceptance of the revised Terms and Conditions and the reasonableness of these standards for notice of changes. For your information, this page was last updated as of the date at the top of these terms and conditions.\n" +
            "2. PRIVACY\n" +
            "Please review our Privacy Policy, which also governs your visit to this Site, to understand our practices.\n" +
            "\n" +
            "3. LINKED SITES\n" +
            "This Site may contain links to other independent third-party Web sites (\"Linked Sites”). These Linked Sites are provided solely as a convenience to our visitors. Such Linked Sites are not under our control, and we are not responsible for and does not endorse the content of such Linked Sites, including any information or materials contained on such Linked Sites. You will need to make your own independent judgment regarding your interaction with these Linked Sites.\n" +
            "\n" +
            "4. FORWARD LOOKING STATEMENTS\n" +
            "All materials reproduced on this site speak as of the original date of publication or filing. The fact that a document is available on this site does not mean that the information contained in such document has not been modified or superseded by events or by a subsequent document or filing. We have no duty or policy to update any information or statements contained on this site and, therefore, such information or statements should not be relied upon as being current as of the date you access this site.\n" +
            "\n" +
            "5. DISCLAIMER OF WARRANTIES AND LIMITATION OF LIABILITY\n" +
            "A. THIS SITE MAY CONTAIN INACCURACIES AND TYPOGRAPHICAL ERRORS. WE DOES NOT WARRANT THE ACCURACY OR COMPLETENESS OF THE MATERIALS OR THE RELIABILITY OF ANY ADVICE, OPINION, STATEMENT OR OTHER INFORMATION DISPLAYED OR DISTRIBUTED THROUGH THE SITE. YOU EXPRESSLY UNDERSTAND AND AGREE THAT: (i) YOUR USE OF THE SITE, INCLUDING ANY RELIANCE ON ANY SUCH OPINION, ADVICE, STATEMENT, MEMORANDUM, OR INFORMATION CONTAINED HEREIN, SHALL BE AT YOUR SOLE RISK; (ii) THE SITE IS PROVIDED ON AN \"AS IS\" AND \"AS AVAILABLE\" BASIS; (iii) EXCEPT AS EXPRESSLY PROVIDED HEREIN WE DISCLAIM ALL WARRANTIES OF ANY KIND, WHETHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, WORKMANLIKE EFFORT, TITLE AND NON-INFRINGEMENT; (iv) WE MAKE NO WARRANTY WITH RESPECT TO THE RESULTS THAT MAY BE OBTAINED FROM THIS SITE, THE PRODUCTS OR SERVICES ADVERTISED OR OFFERED OR MERCHANTS INVOLVED; (v) ANY MATERIAL DOWNLOADED OR OTHERWISE OBTAINED THROUGH THE USE OF THE SITE IS DONE AT YOUR OWN DISCRETION AND RISK; and (vi) YOU WILL BE SOLELY RESPONSIBLE FOR ANY DAMAGE TO YOUR COMPUTER SYSTEM OR FOR ANY LOSS OF DATA THAT RESULTS FROM THE DOWNLOAD OF ANY SUCH MATERIAL.\n" +
            "\n" +
            "B. YOU UNDERSTAND AND AGREE THAT UNDER NO CIRCUMSTANCES, INCLUDING, BUT NOT LIMITED TO, NEGLIGENCE, SHALL WE BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, PUNITIVE OR CONSEQUENTIAL DAMAGES THAT RESULT FROM THE USE OF, OR THE INABILITY TO USE, ANY OF OUR SITES OR MATERIALS OR FUNCTIONS ON ANY SUCH SITE, EVEN IF WE HAVE BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES. THE FOREGOING LIMITATIONS SHALL APPLY NOTWITHSTANDING ANY FAILURE OF ESSENTIAL PURPOSE OF ANY LIMITED REMEDY.\n" +
            "\n" +
            "6. EXCLUSIONS AND LIMITATIONS\n" +
            "SOME JURISDICTIONS DO NOT ALLOW THE EXCLUSION OF CERTAIN WARRANTIES OR THE LIMITATION OR EXCLUSION OF LIABILITY FOR INCIDENTAL OR CONSEQUENTIAL DAMAGES. ACCORDINGLY, OUR LIABILITY IN SUCH JURISDICTION SHALL BE LIMITED TO THE MAXIMUM EXTENT PERMITTED BY LAW.\n" +
            "\n" +
            "7. OUR PROPRIETARY RIGHTS\n" +
            "This Site and all its Contents are intended solely for personal, non-commercial use. Except as expressly provided, nothing within the Site shall be construed as conferring any license under our or any third party's intellectual property rights, whether by estoppel, implication, waiver, or otherwise. Without limiting the generality of the foregoing, you acknowledge and agree that all content available through and used to operate the Site and its services is protected by copyright, trademark, patent, or other proprietary rights. You agree not to: (a) modify, alter, or deface any of the trademarks, service marks, trade dress (collectively \"Trademarks\") or other intellectual property made available by us in connection with the Site; (b) hold yourself out as in any way sponsored by, affiliated with, or endorsed by us, or any of our affiliates or service providers; (c) use any of the Trademarks or other content accessible through the Site for any purpose other than the purpose for which we have made it available to you; (d) defame or disparage us, our Trademarks, or any aspect of the Site; and (e) adapt, translate, modify, decompile, disassemble, or reverse engineer the Site or any software or programs used in connection with it or its products and services.\n" +
            "\n" +
            "The framing, mirroring, scraping or data mining of the Site or any of its content in any form and by any method is expressly prohibited.\n" +
            "\n" +
            "8. INDEMNITY\n" +
            "By using the Site web sites you agree to indemnify us and affiliated entities (collectively \"Indemnities\") and hold them harmless from any and all claims and expenses, including (without limitation) attorney's fees, arising from your use of the Site web sites, your use of the Products and Services, or your submission of ideas and/or related materials to us or from any person's use of any ID, membership or password you maintain with any portion of the Site, regardless of whether such use is authorized by you.\n" +
            "\n" +
            "9. COPYRIGHT AND TRADEMARK NOTICE\n" +
            "Except our generated dummy copy, which is free to use for private and commercial use, all other text is copyrighted. generator.lorem-ipsum.info © 2013, all rights reserved\n" +
            "\n" +
            "10. INTELLECTUAL PROPERTY INFRINGEMENT CLAIMS\n" +
            "It is our policy to respond expeditiously to claims of intellectual property infringement. We will promptly process and investigate notices of alleged infringement and will take appropriate actions under the Digital Millennium Copyright Act (\"DMCA\") and other applicable intellectual property laws. Notices of claimed infringement should be directed to:\n" +
            "\n" +
            "generator.lorem-ipsum.info\n" +
            "\n" +
            "126 Electricov St.\n" +
            "\n" +
            "Kiev, Kiev 04176\n" +
            "\n" +
            "Ukraine\n" +
            "\n" +
            "contact@lorem-ipsum.info\n" +
            "\n" +
            "11. PLACE OF PERFORMANCE\n" +
            "This Site is controlled, operated and administered by us from our office in Kiev, Ukraine. We make no representation that materials at this site are appropriate or available for use at other locations outside of the Ukraine and access to them from territories where their contents are illegal is prohibited. If you access this Site from a location outside of the Ukraine, you are responsible for compliance with all local laws.\n" +
            "\n" +
            "12. GENERAL\n" +
            "A. If any provision of these Terms and Conditions is held to be invalid or unenforceable, the provision shall be removed (or interpreted, if possible, in a manner as to be enforceable), and the remaining provisions shall be enforced. Headings are for reference purposes only and in no way define, limit, construe or describe the scope or extent of such section. Our failure to act with respect to a breach by you or others does not waive our right to act with respect to subsequent or similar breaches. These Terms and Conditions set forth the entire understanding and agreement between us with respect to the subject matter contained herein and supersede any other agreement, proposals and communications, written or oral, between our representatives and you with respect to the subject matter hereof, including any terms and conditions on any of customer's documents or purchase orders.\n" +
            "\n" +
            "B. No Joint Venture, No Derogation of Rights. You agree that no joint venture, partnership, employment, or agency relationship exists between you and us as a result of these Terms and Conditions or your use of the Site. Our performance of these Terms and Conditions is subject to existing laws and legal process, and nothing contained herein is in derogation of our right to comply with governmental, court and law enforcement requests or requirements relating to your use of the Site or information provided to or gathered by us with respect to such use.";
    public static ArrayList<voucherData> favList = new ArrayList<voucherData>();

    public static String response = "{\"list_of_voucher\":[{\"voucher_id\":\"10\",\"voucher_name\":\"free nasi lemak\",\"voucher_qty\":\"200\",\"voucher_value\":\"0\",\"voucher_img\":\"https:\\/\\/honeydewpos.com\\/loycher\\/info\\/img\\/food-658715_640.jpg\"},{\"voucher_id\":\"11\",\"voucher_name\":\"free jasmine tea\",\"voucher_qty\":\"150\",\"voucher_value\":\"0\",\"voucher_img\":\"https:\\/\\/honeydewpos.com\\/loycher\\/info\\/img\\/cup-829527_640.jpg\"},{\"voucher_id\":\"12\",\"voucher_name\":\"new\",\"voucher_qty\":\"125\",\"voucher_value\":\"30\",\"voucher_img\":\"https:\\/\\/honeydewpos.com\\/loycher\\/info\\/img\\/a1.jpg\"},{\"voucher_id\":\"22\",\"voucher_name\":\"a\",\"voucher_qty\":\"1\",\"voucher_value\":\"12\",\"voucher_img\":\"https:\\/\\/honeydewpos.com\\/loycher\\/info\\/img\\/a6.jpg\"},{\"voucher_id\":\"8\",\"voucher_name\":\"carrot juice \",\"voucher_qty\":\"1\",\"voucher_value\":\"0\",\"voucher_img\":\"https:\\/\\/honeydewpos.com\\/loycher\\/info\\/img\\/carrot-juice-1623157_640.jpg\"},{\"voucher_id\":\"9\",\"voucher_name\":\"free coffee\",\"voucher_qty\":\"100\",\"voucher_value\":\"0\",\"voucher_img\":\"https:\\/\\/honeydewpos.com\\/loycher\\/info\\/img\\/coffee-983955_640.jpg\"},{\"voucher_id\":\"9\",\"voucher_name\":\"free coffee\",\"voucher_qty\":\"100\",\"voucher_value\":\"0\",\"voucher_img\":\"https:\\/\\/honeydewpos.com\\/loycher\\/info\\/img\\/coffee-983955_640.jpg\"},{\"voucher_id\":\"9\",\"voucher_name\":\"free coffee\",\"voucher_qty\":\"100\",\"voucher_value\":\"0\",\"voucher_img\":\"https:\\/\\/honeydewpos.com\\/loycher\\/info\\/img\\/coffee-983955_640.jpg\"},{\"voucher_id\":\"9\",\"voucher_name\":\"free coffee\",\"voucher_qty\":\"100\",\"voucher_value\":\"0\",\"voucher_img\":\"https:\\/\\/honeydewpos.com\\/loycher\\/info\\/img\\/coffee-983955_640.jpg\"}]}";

    //signIn method
    public static void registerUser(String url , final String firstName, final String lastName,
                             final String email, final String phone, final String password,final Context context,final Class c) {
        String cancel_req_tag = "register";

        String URL_WITH_PARAMS = url + "?" + "email=" + email + "&pass=" + password + "&phone_no="
                + phone + "&first_name=" + firstName + "&last_name=" + lastName + "&date_of_birth=" + "1995-07-04";


        StringRequest strReq = new StringRequest(Request.Method.GET,
                URL_WITH_PARAMS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    String result = jObj.getString("reg_result");
                    if (result.equals("Successful")) {
                        //String user = jObj.getJSONObject("user").getString("name");
                        // Launch login activity
                        Toast.makeText(context,"successful registration",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context,c);
                        context.startActivity(intent);

                    } else {

                        String errorMsg = jObj.getString("reg_result");
                        Log.e("error massage", "onResponse: "+errorMsg );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ErrorResponse", "Registration Error: " + error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("first_name", firstName);
                params.put("last_name", lastName);
                params.put("email", email);
                params.put("pass", password);
                params.put("phone_no", phone);
                params.put("date_of_birth", "1995/7/4");
                Log.e("Map", "getParams: " + params.toString());
                return params;
            }
        };
        // Adding request to request queue
        requestQueueHelper.getInstance(context).addToRequestQueue(strReq, cancel_req_tag);
    }
}
