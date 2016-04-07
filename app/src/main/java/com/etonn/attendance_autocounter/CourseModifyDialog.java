package com.etonn.attendance_autocounter;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.etonn.attendance_autocounter.db.DBManager;

/**
 * Created by Zeshi on 11/23/14.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CourseModifyDialog extends DialogFragment {
    DBManager db;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        db = new DBManager(getActivity());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        //set custom dialog view, in this case is two edittext box
        final View mView = inflater.inflate(R.layout.dialog_course_modify, null);
        builder.setView(mView)
                .setTitle("Modify Course")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText courseName = (EditText) mView.findViewById(R.id.dialogCourseName);
                                EditText headcount = (EditText) mView.findViewById(R.id.dialogHeadcount);
                                //set input type ---- only number in dialog_course_modify.xml

                                String courseNameS = courseName.getText().toString();
                                String headcountS = headcount.getText().toString();

                                if (courseNameS.equals("") || headcountS.equals("")) {
                                    Toast.makeText(getActivity(), "please fill in both two blanks", Toast.LENGTH_SHORT).show();
                                }
                                //can't modify course name now in DB
                                else {
                                    if (db.queryCourseByName(courseNameS) == null) {
                                        Toast.makeText(getActivity(), "No such course", Toast.LENGTH_SHORT).show();
                                    } else {
                                        int headcountI = Integer.parseInt(headcountS);
                                        db.updateCourseByName(courseNameS, headcountI);
                                    }

                                }
                            }

                        }
                )
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dismiss();
                            }
                        }
                );
        return builder.create();
    }
}
