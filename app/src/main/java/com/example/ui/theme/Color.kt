package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Metra Cybernetic Tech Palette - Refined for Vibrancy
val MetraCyan = Color(0xFF00E5FF)
val MetraCyanDim = Color(0xFF0097A7)
val MetraBlue = Color(0xFF38BDF8)
val MetraIndigo = Color(0xFF818CF8)
val MetraPurple = Color(0xFFA78BFA)
val MetraEmerald = Color(0xFF34D399)
val MetraAmber = Color(0xFFFBBF24)
val MetraRose = Color(0xFFFB7185)

// Dark Surfaces - High Contrast
val DarkObsidian = Color(0xFF020617)
val DarkNavyBg = Color(0xFF0B1120)
val DarkCardSurface = Color(0xFF1E293B).copy(alpha = 0.85f)
val DarkCardBorder = Color(0xFF334155).copy(alpha = 0.6f)
val DarkSurfaceVariant = Color(0xFF1E293B)
val DarkTextPrimary = Color(0xFFF1F5F9)
val DarkTextSecondary = Color(0xFF94A3B8)
val DarkTextMuted = Color(0xFF64748B)

// Light Surfaces - Vibrant & Clean
val LightBg = Color(0xFFF0F4F8)
val LightSurface = Color(0xFFFFFFFF)
val LightSurfaceVariant = Color(0xFFE2E8F0)
val LightTextPrimary = Color(0xFF0F172A)
val LightTextSecondary = Color(0xFF475569)
val LightTextMuted = Color(0xFF94A3B8)
val LightCardBorder = Color(0xFFCBD5E1)

// Glassmorphism & Effects
val GlassWhite = Color(0xFFFFFFFF).copy(alpha = 0.4f)
val GlassPrimary = Color(0xFF00E5FF).copy(alpha = 0.15f)
val GlassIndigo = Color(0xFF6366F1).copy(alpha = 0.15f)

// Gradients - Technical & Modern
val MetraCyanIndigo = listOf(MetraCyan, MetraIndigo)
val MetraIndigoPurple = listOf(MetraIndigo, MetraPurple)
val MetraEmeraldCyan = listOf(MetraEmerald, MetraCyan)
val MetraRoseAmber = listOf(MetraRose, MetraAmber)
val MetraDarkGradient = listOf(DarkNavyBg, DarkObsidian)
val MetraLightGradient = listOf(Color(0xFFFFFFFF), Color(0xFFF0F4F8))
val MetraVibrantLightGradient = listOf(Color(0xFFE0F2FE), Color(0xFFF0F9FF))
