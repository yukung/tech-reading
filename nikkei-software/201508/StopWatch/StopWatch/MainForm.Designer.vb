﻿<Global.Microsoft.VisualBasic.CompilerServices.DesignerGenerated()> _
Partial Class MainForm
    Inherits System.Windows.Forms.Form

    'フォームがコンポーネントの一覧をクリーンアップするために dispose をオーバーライドします。
    <System.Diagnostics.DebuggerNonUserCode()> _
    Protected Overrides Sub Dispose(ByVal disposing As Boolean)
        Try
            If disposing AndAlso components IsNot Nothing Then
                components.Dispose()
            End If
        Finally
            MyBase.Dispose(disposing)
        End Try
    End Sub

    'Windows フォーム デザイナーで必要です。
    Private components As System.ComponentModel.IContainer

    'メモ: 以下のプロシージャは Windows フォーム デザイナーで必要です。
    'Windows フォーム デザイナーを使用して変更できます。  
    'コード エディターを使って変更しないでください。
    <System.Diagnostics.DebuggerStepThrough()> _
    Private Sub InitializeComponent()
        Me.components = New System.ComponentModel.Container()
        Me.displayTimeTextBox = New System.Windows.Forms.TextBox()
        Me.timer = New System.Windows.Forms.Timer(Me.components)
        Me.startButton = New System.Windows.Forms.Button()
        Me.stopButton = New System.Windows.Forms.Button()
        Me.resetButton = New System.Windows.Forms.Button()
        Me.Label1 = New System.Windows.Forms.Label()
        Me.Label2 = New System.Windows.Forms.Label()
        Me.countDownTimeTextBox = New System.Windows.Forms.TextBox()
        Me.SuspendLayout()
        '
        'displayTimeTextBox
        '
        Me.displayTimeTextBox.BackColor = System.Drawing.Color.White
        Me.displayTimeTextBox.Font = New System.Drawing.Font("MS UI Gothic", 27.75!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(128, Byte))
        Me.displayTimeTextBox.Location = New System.Drawing.Point(12, 12)
        Me.displayTimeTextBox.Name = "displayTimeTextBox"
        Me.displayTimeTextBox.ReadOnly = True
        Me.displayTimeTextBox.Size = New System.Drawing.Size(260, 44)
        Me.displayTimeTextBox.TabIndex = 0
        '
        'timer
        '
        '
        'startButton
        '
        Me.startButton.Location = New System.Drawing.Point(12, 102)
        Me.startButton.Name = "startButton"
        Me.startButton.Size = New System.Drawing.Size(73, 25)
        Me.startButton.TabIndex = 1
        Me.startButton.Text = "スタート"
        Me.startButton.UseVisualStyleBackColor = True
        '
        'stopButton
        '
        Me.stopButton.Location = New System.Drawing.Point(106, 102)
        Me.stopButton.Name = "stopButton"
        Me.stopButton.Size = New System.Drawing.Size(73, 25)
        Me.stopButton.TabIndex = 2
        Me.stopButton.Text = "ストップ"
        Me.stopButton.UseVisualStyleBackColor = True
        '
        'resetButton
        '
        Me.resetButton.Location = New System.Drawing.Point(199, 102)
        Me.resetButton.Name = "resetButton"
        Me.resetButton.Size = New System.Drawing.Size(73, 25)
        Me.resetButton.TabIndex = 3
        Me.resetButton.Text = "リセット"
        Me.resetButton.UseVisualStyleBackColor = True
        '
        'Label1
        '
        Me.Label1.AutoSize = True
        Me.Label1.Location = New System.Drawing.Point(10, 79)
        Me.Label1.Name = "Label1"
        Me.Label1.Size = New System.Drawing.Size(91, 12)
        Me.Label1.TabIndex = 4
        Me.Label1.Text = "カウントダウン時間"
        '
        'Label2
        '
        Me.Label2.AutoSize = True
        Me.Label2.Location = New System.Drawing.Point(234, 79)
        Me.Label2.Name = "Label2"
        Me.Label2.Size = New System.Drawing.Size(17, 12)
        Me.Label2.TabIndex = 5
        Me.Label2.Text = "分"
        '
        'countDownTimeTextBox
        '
        Me.countDownTimeTextBox.Location = New System.Drawing.Point(106, 76)
        Me.countDownTimeTextBox.Name = "countDownTimeTextBox"
        Me.countDownTimeTextBox.Size = New System.Drawing.Size(122, 19)
        Me.countDownTimeTextBox.TabIndex = 6
        Me.countDownTimeTextBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Right
        '
        'MainForm
        '
        Me.AutoScaleDimensions = New System.Drawing.SizeF(6.0!, 12.0!)
        Me.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font
        Me.ClientSize = New System.Drawing.Size(284, 261)
        Me.Controls.Add(Me.countDownTimeTextBox)
        Me.Controls.Add(Me.Label2)
        Me.Controls.Add(Me.Label1)
        Me.Controls.Add(Me.resetButton)
        Me.Controls.Add(Me.stopButton)
        Me.Controls.Add(Me.startButton)
        Me.Controls.Add(Me.displayTimeTextBox)
        Me.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle
        Me.Name = "MainForm"
        Me.Text = "MyStopWatch"
        Me.ResumeLayout(False)
        Me.PerformLayout()

    End Sub

    Friend WithEvents displayTimeTextBox As TextBox
    Friend WithEvents timer As Timer
    Friend WithEvents startButton As Button
    Friend WithEvents stopButton As Button
    Friend WithEvents resetButton As Button
    Friend WithEvents Label1 As Label
    Friend WithEvents Label2 As Label
    Friend WithEvents countDownTimeTextBox As TextBox
End Class
