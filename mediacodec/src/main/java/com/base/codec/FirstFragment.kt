package com.base.codec

import android.os.Bundle
import android.os.Environment
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.base.codec.record.AvcDecoder
import com.base.codec.record.VideoDecoderThread
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), SurfaceHolder.Callback {
    private val path = Environment.getExternalStorageDirectory().absolutePath + "/test1.h264"

    private lateinit var surfaceView: SurfaceView
    private lateinit var mSurfaceHolder: SurfaceHolder
    private lateinit var fab: FloatingActionButton

    private lateinit var mVideoDecoder: VideoDecoderThread

    private var isPlay = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mVideoDecoder = VideoDecoderThread()
        surfaceView = view.findViewById(R.id.ijkplayerView)
        initSurface()

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        fab = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            val file = File(path)
            if (!file.exists()) {
                Toast.makeText(requireContext(), "请先录制视频", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isPlay){
                isPlay = true
//                surfaceView.visibility = View.VISIBLE
                AvcDecoder.getInstance(surfaceView.holder.surface).startH264Decode(path)
            } else {
                isPlay = false
//                surfaceView.visibility = View.GONE
                AvcDecoder.getInstance(surfaceView.holder.surface).startH264Decode(path)
            }
        }

    }

    private fun initSurface() {
        mSurfaceHolder = surfaceView.holder
        mSurfaceHolder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {

            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {}
        })
    }

    private fun initManager() {
//        if (isPlayH264) {
            AvcDecoder.getInstance(surfaceView.holder.surface).startH264Decode(path)
//            return
//        }
//        DecoderManager.getInstance().startMP4Decode()
//        AudioPlayManager.getInstance().setContext(ApplicationProvider.getApplicationContext())
//        AudioPlayManager.getInstance().startThread()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        if (mVideoDecoder != null) {
            if (mVideoDecoder.init(holder.surface, path)) {
                mVideoDecoder.start()
            }
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        if (mVideoDecoder != null) {
            mVideoDecoder.close()
            surfaceView.visibility = View.GONE
            fab.visibility = View.VISIBLE
        }
    }
}