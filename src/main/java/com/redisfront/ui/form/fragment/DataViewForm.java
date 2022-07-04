package com.redisfront.ui.form.fragment;

import cn.hutool.core.io.unit.DataSizeUtil;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatEmptyBorder;
import com.formdev.flatlaf.ui.FlatLineBorder;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.redisfront.constant.Enum;
import com.redisfront.constant.UI;
import com.redisfront.model.ConnectInfo;
import com.redisfront.model.TreeNodeInfo;
import com.redisfront.ui.component.TextEditorComponent;
import com.redisfront.ui.form.MainNoneForm;
import com.redisfront.util.Fn;
import com.redisfront.util.LettuceUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * DataViewForm
 *
 * @author Jin
 */
public class DataViewForm {

    private JPanel contentPanel;
    private JTextField keyField;
    private JPanel bodyPanel;
    private JPanel valueViewPanel;
    private JPanel StringViewPanel;
    private JLabel keyTypeLabel;
    private JButton delBtn;
    private JButton refBtn;
    private JLabel lengthLabel;
    private JLabel keySizeLabel;
    private JButton saveBtn;
    private JTextField ttlField;

    private final ConnectInfo connectInfo;
    private TextEditorComponent textEditorComponent;

    public static DataViewForm newInstance(ConnectInfo connectInfo) {
        return new DataViewForm(connectInfo);
    }

    public DataViewForm(ConnectInfo connectInfo) {
        this.connectInfo = connectInfo;
        $$$setupUI$$$();
        Fn.removeAllComponent(bodyPanel);
        bodyPanel.add(MainNoneForm.getInstance().getContentPanel());
        keyTypeLabel.setOpaque(true);
        keyTypeLabel.setForeground(Color.WHITE);
        keyTypeLabel.setBorder(new EmptyBorder(2, 3, 2, 3));

        var keyLabel = new JLabel();
        keyLabel.setText("KEY:");
        keyLabel.setBorder(new EmptyBorder(2, 2, 2, 2));
        keyField.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_COMPONENT, keyLabel);

        var ttlLabel = new JLabel();
        ttlLabel.setText("TTL:");
        ttlLabel.setBorder(new EmptyBorder(2, 2, 2, 2));
        ttlField.setSize(5, -1);
        ttlField.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_COMPONENT, ttlLabel);

        delBtn.setIcon(UI.DELETE_ICON);
        delBtn.setText("删除");
        delBtn.setToolTipText("删除键");
        refBtn.setIcon(UI.REFRESH_ICON);
        refBtn.setText("重载");
        refBtn.setToolTipText("重载");

        saveBtn.setIcon(UI.SAVE_ICON);
        saveBtn.setText("保存");
        saveBtn.setToolTipText("保存");
    }

    public JPanel contentPanel() {
        return contentPanel;
    }

    public void dataChange(TreeNodeInfo treeNodeInfo) {
        if (bodyPanel.getComponentCount() == 1) {
            Fn.removeAllComponent(bodyPanel);
            bodyPanel.add(StringViewPanel, BorderLayout.NORTH);
            bodyPanel.add(valueViewPanel, BorderLayout.CENTER);
        }

        this.keyField.setText(treeNodeInfo.key());

        LettuceUtil.run(connectInfo, redisCommands -> {
            Long ttl = redisCommands.ttl(treeNodeInfo.key());
            ttlField.setText(ttl.toString());
            String type = redisCommands.type(treeNodeInfo.key());
            Enum.KeyTypeEnum keyTypeEnum = Enum.KeyTypeEnum.valueOf(type.toUpperCase());

            if (keyTypeEnum == Enum.KeyTypeEnum.STRING) {

                Long strLen = redisCommands.strlen(treeNodeInfo.key());
                lengthLabel.setText("Length: " + strLen);

                keyTypeLabel.setText(keyTypeEnum.name());
                keyTypeLabel.setBackground(keyTypeEnum.color());

                String value = redisCommands.get(treeNodeInfo.key());
                keySizeLabel.setText("Size: " + DataSizeUtil.format(value.getBytes().length));
                textEditorComponent.textArea().setText(value);
            }


        });
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(0, 0));
        contentPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        bodyPanel.setLayout(new BorderLayout(0, 0));
        contentPanel.add(bodyPanel, BorderLayout.CENTER);
        StringViewPanel.setLayout(new GridLayoutManager(2, 9, new Insets(0, 0, 0, 0), -1, -1));
        bodyPanel.add(StringViewPanel, BorderLayout.NORTH);
        StringViewPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        keyTypeLabel = new JLabel();
        keyTypeLabel.setText("Label");
        StringViewPanel.add(keyTypeLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        StringViewPanel.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        keyField = new JTextField();
        StringViewPanel.add(keyField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lengthLabel = new JLabel();
        lengthLabel.setText("Label");
        StringViewPanel.add(lengthLabel, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        keySizeLabel = new JLabel();
        keySizeLabel.setText("Label");
        StringViewPanel.add(keySizeLabel, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        delBtn = new JButton();
        delBtn.setText("");
        StringViewPanel.add(delBtn, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        refBtn = new JButton();
        refBtn.setText("");
        StringViewPanel.add(refBtn, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveBtn = new JButton();
        saveBtn.setText("");
        StringViewPanel.add(saveBtn, new GridConstraints(0, 7, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ttlField = new JTextField();
        StringViewPanel.add(ttlField, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer2 = new Spacer();
        StringViewPanel.add(spacer2, new GridConstraints(0, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        bodyPanel.add(valueViewPanel, BorderLayout.CENTER);
        valueViewPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 10, 8, 10), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPanel;
    }

    private void createUIComponents() {
        bodyPanel = new JPanel() {
            @Override
            public void updateUI() {
                super.updateUI();
                var flatLineBorder = new FlatLineBorder(new Insets(0, 2, 0, 0), UIManager.getColor("Component.borderColor"));
                setBorder(flatLineBorder);
            }
        };
        valueViewPanel = new JPanel() {
            @Override
            public void updateUI() {
                super.updateUI();
            }

            {
                setLayout(new BorderLayout());
            }
        };
        valueViewPanel.add(new JPanel() {
            @Override
            public void updateUI() {
                super.updateUI();
                setLayout(new BorderLayout());
                setBorder(new FlatEmptyBorder(0, 0, 5, 0));
                add(new JSeparator(), BorderLayout.CENTER);
            }
        }, BorderLayout.NORTH);

        textEditorComponent = TextEditorComponent.newInstance();
        valueViewPanel.add(textEditorComponent, BorderLayout.CENTER);

        valueViewPanel.add(new JPanel() {
            @Override
            public void updateUI() {
                super.updateUI();
                setLayout(new BorderLayout());
                setBorder(new FlatEmptyBorder(5, 0, 5, 0));
                add(new JSeparator(), BorderLayout.CENTER);
            }
        }, BorderLayout.SOUTH);

        StringViewPanel = new JPanel() {
            @Override
            public void updateUI() {
                super.updateUI();
            }

            {
                setLayout(new BorderLayout());
            }
        };

    }


}
