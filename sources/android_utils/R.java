package android_utils;

import com.chaquo.python.PyCtorMarker;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.StaticProxy;

/* JADX INFO: loaded from: classes.dex */
public class R implements Runnable, StaticProxy {
    private PyObject _chaquopyDict;

    static {
        Python.getInstance().getModule("android_utils").get((Object) "R");
    }

    public R() {
        PyObject pyObject_chaquopyCall = PyObject._chaquopyCall(this, "__init__", new Object[0]);
        if (pyObject_chaquopyCall != null) {
            pyObject_chaquopyCall.toJava(Void.TYPE);
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        PyObject pyObject_chaquopyCall = PyObject._chaquopyCall(this, "run", new Object[0]);
        if (pyObject_chaquopyCall != null) {
            pyObject_chaquopyCall.toJava(Void.TYPE);
        }
    }

    public R(PyCtorMarker pyCtorMarker) {
    }

    @Override // com.chaquo.python.PyProxy
    public PyObject _chaquopyGetDict() {
        return this._chaquopyDict;
    }

    @Override // com.chaquo.python.PyProxy
    public void _chaquopySetDict(PyObject pyObject) {
        this._chaquopyDict = pyObject;
    }
}
