package com.example.myapplication;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class DebugPanelController {

    private final LinearLayout debugPanel;

    private final TextView debugText;

    private boolean visible = false;

    /* debugPanelControllerコンストラクタ */
    public DebugPanelController(LinearLayout debugPanel,TextView debugText) {
        this.debugPanel = debugPanel;
        this.debugText  = debugText;
        this.debugPanel.setVisibility(View.GONE);
    }

    /* debugPanel表示切替 */
    public void toggle() {
        this.visible = !this.visible;
        this.debugPanel.setVisibility(this.visible ? View.VISIBLE : View.GONE);
    }

    /* debugPanel表示内容設定・更新 */
    /* 汎用性重視のため、nullチェック等は本メソッド内で実施　 */
    public void updateDebugInfo(
            String eventName,
            LatLng gpsCcpPosition,
            LocationController locationController,
            int selectedCcpId,
            String selectedCcpImageName,
            int selectedGoalId,
            String selectedGoalImageName
    ) {
        if( locationController == null) {
            return;
        }

        debugText.setText(
                "EVENT : " + eventName +
                        "\nGPS : " + gpsCcpPosition +
                        "\nPermission : " + locationController.hasLocationPermission() +
                        "\nCCP : " +
                        "\n\tccpId : " + selectedCcpId +
                        "\n\tccpImageName : " + selectedCcpImageName +
                        "\nGOAL : " +
                        "\n\tgoalId : " + selectedGoalId +
                        "\n\tgoalImageId : " + selectedGoalImageName
        );
    }
}