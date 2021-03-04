package com.temicanada.tdsb.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.DisplayMetrics
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.temicanada.tdsb.MainActivity
import com.temicanada.tdsb.R
import com.temicanada.tdsb.model.Company


class CompanyAdapter(private var companyList: ArrayList<Company>, private var context: Context) :
    RecyclerView.Adapter<CompanyAdapter.MyViewHolder>() {
    private val TAG: String = "AppDebug"

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewCompany: TextView = itemView.findViewById(R.id.textViewCompanyName) as TextView
        var textViewDescription: TextView =
            itemView.findViewById(R.id.textViewDescription) as TextView
        var logo: ImageView = itemView.findViewById(R.id.logo) as ImageView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = companyList[position]
        print(item.packageName)
        holder.textViewCompany.text = item.companyName
        holder.textViewDescription.text = item.description
//        holder.logo.setBackground(ContextCompat.getDrawable(context,companyList[position].logo))
        Glide.with(context).load(item.logo).override(300, 300).into(holder.logo)
        holder.logo.setOnClickListener(View.OnClickListener {

            if (item.companyName.equals("Euler 4.0") && item.packageName.equals("")) {

                // custom dialog
                val dialog = Dialog(context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.introvid)
                dialog.show()

                val lp = WindowManager.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
                )

                val videoview = dialog.findViewById(R.id.surface_view) as VideoView
                val uri: Uri =
                    Uri.parse("android.resource://" + "com.temicanada.tdsb" + "/" + R.raw.euler)
                videoview.setVideoURI(uri)
                videoview.start()

            } else {
                if (context.isPackageInstalled(item.packageName)) {
                    val launchIntent: Intent? =
                        context.packageManager.getLaunchIntentForPackage(item.packageName)
                    context.startActivity(launchIntent)
                } else {
                    Toast.makeText(
                        context,
                        "Application Not Found. Please install the application first.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        })
    }

    override fun getItemCount(): Int {
        return companyList.size
    }

    private fun Context.isPackageInstalled(packageName: String): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

}