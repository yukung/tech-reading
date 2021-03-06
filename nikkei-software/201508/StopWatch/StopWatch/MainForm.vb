﻿Public Class MainForm

    Private startTime As DateTime

    Private lapseTime As TimeSpan = TimeSpan.Zero

    Private countDownTime As TimeSpan = TimeSpan.FromMinutes(5.0)

    Private isTimerRunning As Boolean = False

    Private Function GetDisplayTimeString() As String
        Dim format As String = "mm\:ss\.f"
        Return GetRemainingTime.ToString(format)
    End Function

    Private Function GetRemainingTime() As TimeSpan
        Dim remainingTime As TimeSpan = countDownTime - lapseTime
        If remainingTime < TimeSpan.Zero Then
            Return TimeSpan.Zero
        Else
            Return remainingTime
        End If
    End Function

    Private Sub UpdateCountDownTimeTextBox()
        countDownTimeTextBox.Text = countDownTime.Minutes.ToString()
        countDownTimeTextBox.ReadOnly = isTimerRunning
    End Sub

    Private Sub UpdateUI()
        displayTimeTextBox.Text = GetDisplayTimeString()
        EnableButtons()
        UpdateCountDownTimeTextBox()
    End Sub

    Private Sub StopTimer()
        timer.Stop()
        isTimerRunning = False
        UpdateUI()
    End Sub

    Private Sub EnableButtons()
        startButton.Enabled = Not isTimerRunning
        stopButton.Enabled = isTimerRunning
        resetButton.Enabled = lapseTime <> TimeSpan.Zero
    End Sub

    Private Sub CheckTime()
        If GetRemainingTime() = TimeSpan.Zero Then
            StopTimer()
            Media.SystemSounds.Asterisk.Play()
        End If
    End Sub

    Private Sub MainForm_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        UpdateUI()
    End Sub

    Private Sub timer_Tick(sender As Object, e As EventArgs) Handles timer.Tick
        lapseTime = DateTime.Now - startTime
        UpdateUI()
        CheckTime()
    End Sub

    Private Sub startButton_Click(sender As Object, e As EventArgs) Handles startButton.Click
        startTime = DateTime.Now
        isTimerRunning = True
        timer.Start()
    End Sub

    Private Sub stopButton_Click(sender As Object, e As EventArgs) Handles stopButton.Click
        StopTimer()
    End Sub

    Private Sub resetButton_Click(sender As Object, e As EventArgs) Handles resetButton.Click
        lapseTime = TimeSpan.Zero
        StopTimer()
    End Sub

    Private Sub countDownTimeTextBox_TextChanged(sender As Object, e As EventArgs) Handles countDownTimeTextBox.TextChanged
        Dim countDownTimeMinites As Integer
        If Integer.TryParse(countDownTimeTextBox.Text, countDownTimeMinites) Then
            countDownTime = TimeSpan.FromMinutes(countDownTimeMinites)
            UpdateUI()
        End If
    End Sub
End Class
